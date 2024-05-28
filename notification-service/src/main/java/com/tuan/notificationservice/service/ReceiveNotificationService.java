package com.tuan.notificationservice.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuan.notificationservice.dto.emaildto.EmailDetailRequest;
import com.tuan.notificationservice.dto.emaildto.MessageUserResponse;

import com.tuan.notificationservice.dto.notificationdto.MessageLoginResponse;
import com.tuan.notificationservice.entity.MessageUser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiveNotificationService {
    EmailService emailService;
    ObjectMapper kafkaObjectMapper;
    MessageService messageService;
    @KafkaListener(
            topics = "${app.redis-topic.balance_account_change}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(ConsumerRecord<String,String> record) throws JsonProcessingException {
        MessageUserResponse notificationResponse =
                kafkaObjectMapper.readValue(record.value(), MessageUserResponse.class);
        String operator = (notificationResponse.getPaymentType().equals("DEPOSIT")
                || notificationResponse.getPaymentType().equals("RECEIVE")) ? "+" : "-";
        String body = "Balance change " + notificationResponse.getDatePayment() + " " + record.key() +": " + operator + notificationResponse.getAmount();
        List<String> registrationTokens = notificationResponse.getRegistrationTokens();
        log.info(body);
        pushNotificationBalanceChange(body, registrationTokens);
    }
    public void pushNotificationBalanceChange(String body, List<String> registrationTokens){
        MessageUser messageUser = MessageUser.builder()
                .title("Balance change")
                .body(body)
                .image("uri image")
                .registrationTokens(registrationTokens)
                .build();
        messageService.pushNotification(messageUser);
    }
    @KafkaListener(
            topics = "${app.redis-topic.login}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenLogin(ConsumerRecord<String,String> record) throws JsonProcessingException {
        MessageLoginResponse notificationResponse =
                kafkaObjectMapper.readValue(record.value(), MessageLoginResponse.class);
        EmailDetailRequest emailDetailRequest = EmailDetailRequest.builder()
                .recipient(notificationResponse.getEmail())
                .subject("Notice to log in to EBank")
                .messageBody("You have just signed in on the device")
                .build();
        emailService.sendEmailAlert(emailDetailRequest);
    }
}

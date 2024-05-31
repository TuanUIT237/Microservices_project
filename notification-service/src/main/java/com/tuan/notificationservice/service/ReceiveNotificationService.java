package com.tuan.notificationservice.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuan.notificationservice.dto.emaildto.EmailDetailRequest;
import com.tuan.notificationservice.dto.notificationdto.MessageAccountResponse;

import com.tuan.notificationservice.dto.notificationdto.MessageCreditCardResponse;
import com.tuan.notificationservice.dto.notificationdto.MessageLoanResponse;
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
            topics = "${app.kafka-topic.balance_account_change}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenBalanceChange(ConsumerRecord<String,String> record) throws JsonProcessingException {
        MessageAccountResponse notificationResponse =
                kafkaObjectMapper.readValue(record.value(), MessageAccountResponse.class);
        String title ="Balance change";
        String operator = (notificationResponse.getPaymentType().equals("DEPOSIT")
                || notificationResponse.getPaymentType().equals("RECEIVE")) ? "+" : "-";
        String body = "Balance change " + notificationResponse.getDatePayment() + " " + record.key() +": " + operator + notificationResponse.getAmount();
        List<String> registrationTokens = notificationResponse.getRegistrationTokens();
        log.info(body);
        pushNotificationBalanceChange(title, body, registrationTokens);
    }
    public void pushNotificationBalanceChange(String title, String body, List<String> registrationTokens){
        MessageUser messageUser = MessageUser.builder()
                .title(title)
                .body(body)
                .image("uri image")
                .registrationTokens(registrationTokens)
                .build();
        messageService.pushNotification(messageUser);
    }
    @KafkaListener(
            topics = "${app.kafka-topic.login}",
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
    @KafkaListener(
            topics = "${app.kafka-topic.credit_available_limit_change}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenAvailableLimit(ConsumerRecord<String,String> record) throws JsonProcessingException {
        MessageCreditCardResponse notificationResponse =
                kafkaObjectMapper.readValue(record.value(), MessageCreditCardResponse.class);
        String body = "Credit card payment: " +
                notificationResponse.getDatePayment() + " " +
                record.key() +": -" +
                notificationResponse.getAmount() + "\n" + "Available limit after payment: "+
                notificationResponse.getAvailableLimit();
        String title = "Notification about available limit";
        List<String> registrationTokens = notificationResponse.getRegistrationTokens();
        log.info(body);
        pushNotificationBalanceChange(title, body, registrationTokens);
    }
    @KafkaListener(
            topics = "${app.kafka-topic.debt_loan}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenDebtLoan(ConsumerRecord<String,String> record) throws JsonProcessingException {
        MessageLoanResponse notificationResponse =
                kafkaObjectMapper.readValue(record.value(), MessageLoanResponse.class);
        String operator = notificationResponse.getPaymentType().equals("LATE") ? "+" : "-";
        String title = "Notification about remaining debt";
        String body = "Update principal" +
                notificationResponse.getDatePayment() + " " +
                record.key() +": " + operator +
                notificationResponse.getAmount() + "\n" +
                notificationResponse.getRemainingDebt();
        List<String> registrationTokens = notificationResponse.getRegistrationTokens();
        log.info(body);
        pushNotificationBalanceChange(title,body, registrationTokens);
    }
}

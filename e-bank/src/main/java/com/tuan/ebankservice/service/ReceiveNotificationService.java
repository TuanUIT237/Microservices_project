package com.tuan.ebankservice.service;


import com.tuan.ebankservice.dto.emaildto.EmailDetailRequest;
import com.tuan.ebankservice.dto.notificationdto.NotificationRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReceiveNotificationService {
    EmailService emailService;
    @KafkaListener(
            topics = "${spring.kafka.template.default-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(NotificationRequest request) {
        String operator = (request.getPaymentType().equals("DEPOSIT")
                || request.getPaymentType().equals("RECEIVE")) ? "+" : "-";
//        EmailDetailRequest emailDetails = EmailDetailRequest.builder()
//                .recipient("23072002td@gmail.com")
//                .subject("BALANCE CHANGE")
//                .messageBody("Balance change " + request.getDatePayment() + " " + request.getAccountId() + ": " + operator + request.getAmount())
//                .build();
//        emailService.sendEmailAlert(emailDetails);
        log.info("Balance change " + request.getDatePayment() + " " + request.getAccountId() + ": " + operator + request.getAmount());
    }

}

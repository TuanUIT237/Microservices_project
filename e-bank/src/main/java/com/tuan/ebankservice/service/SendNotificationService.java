package com.tuan.ebankservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuan.ebankservice.dto.notificationdto.MessageAccountRequest;
import com.tuan.ebankservice.dto.notificationdto.MessageCreditCardRequest;
import com.tuan.ebankservice.dto.notificationdto.MessageLoanRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SendNotificationService {
    @Value("${app.kafka-topic.balance_account_change}")
    @NonFinal
    String balance_account_change;

    @Value("${app.kafka-topic.credit_available_limit_change}")
    @NonFinal
    String credit_available_limit_change;

    @Value("${app.kafka-topic.debt_loan}")
    @NonFinal
    String debt_loan;

    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper kafkaObjectMapper;

    public void sendBalance(String id, MessageAccountRequest request) throws JsonProcessingException {
        String message = kafkaObjectMapper.writeValueAsString(request);
        kafkaTemplate.send(balance_account_change, id, message);
    }

    public void sendAvailableLimit(String id, MessageCreditCardRequest request) throws JsonProcessingException {
        String message = kafkaObjectMapper.writeValueAsString(request);
        kafkaTemplate.send(credit_available_limit_change, id, message);
    }

    public void sendDebtLoan(String id, MessageLoanRequest request) throws JsonProcessingException {
        String message = kafkaObjectMapper.writeValueAsString(request);
        kafkaTemplate.send(debt_loan, id, message);
    }
}

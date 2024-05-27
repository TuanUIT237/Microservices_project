package com.tuan.ebankservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuan.ebankservice.dto.notificationdto.MessageUserRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class SendNotificationService {
    @Value("${spring.kafka.template.default-topic}")
    @NonFinal
    String topic;

    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper kafkaObjectMapper;

    public void sendBalance(String id, MessageUserRequest request) throws JsonProcessingException {
        String message = kafkaObjectMapper.writeValueAsString(request);
        kafkaTemplate.send(topic,id,message);
    }
}

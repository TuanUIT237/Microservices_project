package com.tuan.ebankservice.service;

import com.tuan.ebankservice.dto.notificationdto.NotificationRequest;
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

    KafkaTemplate<String, NotificationRequest> kafkaTemplate;

    public void sendBalance(NotificationRequest request){
        kafkaTemplate.send(topic, request);
    }
}

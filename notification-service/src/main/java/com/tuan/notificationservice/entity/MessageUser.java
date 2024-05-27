package com.tuan.notificationservice.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
import java.util.Map;

@Document(collection = "message_user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageUser {
    @MongoId
    String id;
    String title;
    String body;
    String image;
    Map<String,String> data;
    List<String> registrationTokens;
}

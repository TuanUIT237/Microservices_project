package com.tuan.notificationservice.service;

import com.google.firebase.messaging.*;
import com.tuan.notificationservice.dto.notificationdto.MessageGlobalRequest;
import com.tuan.notificationservice.dto.notificationdto.MessageGlobalResponse;
import com.tuan.notificationservice.entity.MessageGlobal;
import com.tuan.notificationservice.entity.MessageUser;
import com.tuan.notificationservice.mapper.MessageGlobalMapper;
import com.tuan.notificationservice.repository.MessageGlobalRepository;
import com.tuan.notificationservice.repository.MessageUserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MessageService {
    MessageGlobalRepository messageGlobalRepository;
    MessageGlobalMapper messageGlobalMapper;
    FirebaseMessaging firebaseMessaging;
    public MessageGlobalResponse createNotification(MessageGlobalRequest request){
        MessageGlobal messageGlobal = messageGlobalMapper.toMessageGlobal(request);
        pushNotificationGlobal(messageGlobal);
        return messageGlobalMapper.toMessageGlobalResponse(messageGlobalRepository.save(messageGlobal));
    }
    public List<MessageGlobalResponse> getNotifications(){
        return messageGlobalRepository.findAll().stream().map(messageGlobalMapper::toMessageGlobalResponse).toList();
    }
    public void pushNotification(MessageUser message) {
        List<String> registrationTokens=message.getRegistrationTokens();
        Notification notification = Notification.builder()
                .setTitle(message.getTitle())
                .setBody(message.getBody())
                .setImage(message.getImage())
                .build();

        MulticastMessage multicastMessage = MulticastMessage.builder()
                .addAllTokens(registrationTokens)
                .setNotification(notification)
                .build();
        try {
            firebaseMessaging.sendMulticast(multicastMessage);
        } catch (FirebaseMessagingException e) {
            log.info("Firebase error {}", e.getMessage());
        }
    }
    public void pushNotificationGlobal(MessageGlobal messageGlobal) {
        Notification notification = Notification.builder()
                .setTitle(messageGlobal.getTitle())
                .setBody(messageGlobal.getBody())
                .setImage(messageGlobal.getImage())
                .build();

        Message message = Message.builder()
                .setTopic("all")
                .setNotification(notification)
                .build();
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            log.info("Firebase error {}", e.getMessage());
        }
    }
}

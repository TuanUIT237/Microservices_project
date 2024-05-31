package com.tuan.notificationservice.controller;


import com.tuan.notificationservice.dto.notificationdto.MessageGlobalRequest;
import com.tuan.notificationservice.dto.notificationdto.MessageGlobalResponse;
import com.tuan.notificationservice.service.MessageService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MessageController {
    MessageService messageService;
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping("/create")
    MessageGlobalResponse createNotification(@RequestBody MessageGlobalRequest request){
        return messageService.createNotification(request);
    }
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/")
    List<MessageGlobalResponse> getNotifications(){
        return messageService.getNotifications();
    }
}

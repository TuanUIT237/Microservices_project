package com.tuan.identityservice.dto.notificationdto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageLoginRequest {
    String email;
    String phone;
}

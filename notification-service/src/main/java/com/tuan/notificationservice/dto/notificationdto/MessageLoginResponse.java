package com.tuan.notificationservice.dto.notificationdto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageLoginResponse {
    String email;
    String phone;
}

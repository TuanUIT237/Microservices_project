package com.tuan.notificationservice.dto.notificationdto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageGlobalRequest {
    String title;
    String body;
    String image;
    Map<String,String> data;
}

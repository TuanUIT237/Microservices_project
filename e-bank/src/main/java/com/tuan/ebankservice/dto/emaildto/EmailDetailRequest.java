package com.tuan.ebankservice.dto.emaildto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailDetailRequest {
    String recipient;
    String messageBody;
    String subject;
    String attachment;
}

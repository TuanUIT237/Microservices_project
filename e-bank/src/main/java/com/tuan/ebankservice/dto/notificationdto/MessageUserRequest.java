package com.tuan.ebankservice.dto.notificationdto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageUserRequest {
    BigDecimal amount;
    String paymentType;
    LocalDateTime datePayment;
    BigDecimal balanceAfterPayment;
    List<String> registrationTokens;
}

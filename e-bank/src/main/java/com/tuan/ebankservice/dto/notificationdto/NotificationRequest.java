package com.tuan.ebankservice.dto.notificationdto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    String accountId;
    BigDecimal amount;
    String paymentType;
    LocalDateTime datePayment;
    BigDecimal balanceAfterPayment;
}

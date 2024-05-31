package com.tuan.ebankservice.dto.notificationdto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageLoanRequest {
    BigDecimal amount;
    String paymentType;
    LocalDateTime datePayment;
    BigDecimal remainingDebt;
    List<String> registrationTokens;
}

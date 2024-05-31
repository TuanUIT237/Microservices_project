package com.tuan.ebankservice.dto.creditcarddto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardPaymentResponse {
    String id;
    String creditCardId;
    BigDecimal amount;
    LocalDateTime paymentDate;
    String description;
    String paymentType;
}

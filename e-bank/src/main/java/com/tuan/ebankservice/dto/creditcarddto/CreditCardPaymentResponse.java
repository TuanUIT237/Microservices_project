package com.tuan.ebankservice.dto.creditcarddto;

import com.tuan.ebankservice.util.CreditPaymentType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;


import java.math.BigDecimal;

import java.time.LocalDateTime;

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

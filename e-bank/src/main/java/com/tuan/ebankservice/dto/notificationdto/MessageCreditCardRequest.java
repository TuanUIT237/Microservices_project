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
public class MessageCreditCardRequest {
    BigDecimal amount;
    String paymentType;
    LocalDateTime datePayment;
    BigDecimal availableLimit;
    List<String> registrationTokens;
}

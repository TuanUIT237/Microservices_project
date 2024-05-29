package com.tuan.ebankservice.dto.creditcarddto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

import com.tuan.ebankservice.validator.BigDecimalLength;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardPaymentRequest {
    @NotBlank(message = "CREDIT_CARD_ID_INVALID")
    String creditCardId;

    @BigDecimalLength(min = 10000, message = "AMOUNT_INVALID")
    BigDecimal amount;

    String description;
}

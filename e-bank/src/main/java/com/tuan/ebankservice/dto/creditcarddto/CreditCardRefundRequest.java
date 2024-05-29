package com.tuan.ebankservice.dto.creditcarddto;

import jakarta.validation.constraints.NotBlank;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardRefundRequest {
    @NotBlank(message = "CREDIT_PAYMENT_ID_INVALID")
    String creditCardPaymentId;

    String description;
}

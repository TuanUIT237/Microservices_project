package com.tuan.ebankservice.dto.creditcarddto;

import com.tuan.ebankservice.util.CreditPaymentType;
import com.tuan.ebankservice.validator.BigDecimalLength;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardPaymentRequest {
    @NotBlank(message = "CREDIT_CARD_ID_INVALID")
    String creditCardId;
    @BigDecimalLength(min = 10000,message = "AMOUNT_INVALID")
    BigDecimal amount;
    String description;
}

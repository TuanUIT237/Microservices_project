package com.tuan.ebankservice.dto.creditcarddto;

import com.tuan.ebankservice.validator.BigDecimalLength;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardAddRequest {
    @NotBlank(message = "CREDIT_CARD_ID_INVALID")
    String creditCardId;
    @NotBlank(message = "CVV_NOT_NULL")
    String cvvNo;
    @BigDecimalLength(min = 1,message = "AMOUNT_INVALID")
    BigDecimal amount;
    String description;
}

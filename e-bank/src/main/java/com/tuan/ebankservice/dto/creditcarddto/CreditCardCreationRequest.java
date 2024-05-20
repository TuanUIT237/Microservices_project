package com.tuan.ebankservice.dto.creditcarddto;


import com.tuan.ebankservice.validator.BigDecimalLength;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CreditCardCreationRequest {
    @BigDecimalLength(min = 100000, message = "CREDIT_CARD_LIMIT_INVALID")
    @NotNull(message = "CREDIT_CARD_LIMIT_NOTNULL")
    BigDecimal limit;
    @Min(value = 1, message = "MIN_DAY_OF_CUTOFF_INVALID")
    @Max(value = 29, message = "MAX_DAY_OF_CUTOFF_INVALID")
    Integer dayOfCutOff;
}

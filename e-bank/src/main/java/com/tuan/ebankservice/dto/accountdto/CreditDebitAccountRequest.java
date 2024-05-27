package com.tuan.ebankservice.dto.accountdto;

import com.tuan.ebankservice.validator.BigDecimalLength;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditDebitAccountRequest {
    @NotNull(message = "ACCOUNT_NUMBER_NOT_NULL")
    String accountNumber;
    @BigDecimalLength(min = 10000,message = "AMOUNT_INVALID")
    BigDecimal amount;
}

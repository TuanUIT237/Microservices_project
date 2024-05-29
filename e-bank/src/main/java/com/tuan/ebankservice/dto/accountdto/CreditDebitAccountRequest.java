package com.tuan.ebankservice.dto.accountdto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

import com.tuan.ebankservice.validator.BigDecimalLength;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditDebitAccountRequest {
    @NotNull(message = "ACCOUNT_NUMBER_NOT_NULL")
    String accountNumber;

    @BigDecimalLength(min = 10000, message = "AMOUNT_INVALID")
    BigDecimal amount;
}

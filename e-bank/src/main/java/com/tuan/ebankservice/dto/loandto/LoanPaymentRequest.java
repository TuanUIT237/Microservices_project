package com.tuan.ebankservice.dto.loandto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;

import com.tuan.ebankservice.validator.BigDecimalLength;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanPaymentRequest {
    @NotBlank(message = "LOAN_ID_NOT_NULL")
    String loanId;

    @BigDecimalLength(min = 10000, message = "AMOUNT_INVALID")
    BigDecimal amount;

    String description;
}

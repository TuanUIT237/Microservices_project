package com.tuan.ebankservice.dto.loandto;

import com.tuan.ebankservice.validator.BigDecimalLength;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanPaymentRequest {
    @NotBlank(message = "LOAN_ID_NOT_NULL")
    String loanId;
    @BigDecimalLength(min = 10000,message = "AMOUNT_INVALID")
    BigDecimal amount;
    String description;
}

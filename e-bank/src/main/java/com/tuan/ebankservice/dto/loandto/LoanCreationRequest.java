package com.tuan.ebankservice.dto.loandto;

import com.tuan.ebankservice.validator.BigDecimalLength;
import jakarta.validation.constraints.Max;
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
public class LoanCreationRequest {
    @BigDecimalLength(min = 1000000,message = "PRINCIPAL_LOAN_AMOUNT_INVALID")
    @NotNull(message = "PRINCIPAL_LOAN_AMOUNT_NOT_NULL")
    BigDecimal principalLoanAmount;
    @Min(value = 1,message = "INSTALLMENT_COUNT_MIN_INVALID")
    @Max(value = 360,message = "INSTALLMENT_COUNT_MIN_INVALID")
    Integer installmentCount;
}

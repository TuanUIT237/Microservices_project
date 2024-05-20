package com.tuan.ebankservice.dto.loandto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanResponse {
    String id;
    Integer installmentCount;
    BigDecimal principalLoanAmount;
    BigDecimal monthlyInstallmentAmount;
    BigDecimal interestToBePaid;
    BigDecimal principalToBePaid;
    BigDecimal remainingPrincipal;
    LocalDate dueDate;
    String status;
}

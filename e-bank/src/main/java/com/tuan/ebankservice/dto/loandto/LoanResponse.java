package com.tuan.ebankservice.dto.loandto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String userId;
    String password;
    String status;
}

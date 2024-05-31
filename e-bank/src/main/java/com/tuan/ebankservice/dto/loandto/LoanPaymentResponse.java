package com.tuan.ebankservice.dto.loandto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanPaymentResponse {
    String id;
    String loanId;
    BigDecimal amount;
    LocalDateTime paymentDate;
    String description;
}

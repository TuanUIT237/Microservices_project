package com.tuan.ebankservice.dto.loandto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

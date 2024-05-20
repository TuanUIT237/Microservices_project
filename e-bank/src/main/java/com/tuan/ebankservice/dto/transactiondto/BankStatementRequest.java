package com.tuan.ebankservice.dto.transactiondto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankStatementRequest {
    String accountNumber;
    LocalDate startDate;
    LocalDate endDate;
}

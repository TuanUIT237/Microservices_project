package com.tuan.ebankservice.dto.transactiondto;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

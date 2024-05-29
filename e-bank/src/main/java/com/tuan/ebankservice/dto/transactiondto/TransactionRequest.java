package com.tuan.ebankservice.dto.transactiondto;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {
    BigDecimal balanceAfterTransfer;
    BigDecimal amount;
    String accountNumberfrom;
    String accountNumberto;
    String description;
}

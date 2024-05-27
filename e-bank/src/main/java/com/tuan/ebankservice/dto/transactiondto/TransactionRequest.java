package com.tuan.ebankservice.dto.transactiondto;

import com.tuan.ebankservice.validator.BigDecimalLength;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

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

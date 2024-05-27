package com.tuan.ebankservice.dto.accountdto;

import com.tuan.ebankservice.validator.BigDecimalLength;
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
public class TransferRequest {
    @NotBlank(message = "SOURCE_ACCOUNT_NUMBER_NOT_NULL")
    String sourceAccountNumber;
    @NotBlank(message = "DESTINATION_ACCOUNT_NUMBER_NOT_NULL")
    String destinationAccountNumber;
    @BigDecimalLength(min = 10000,message = "AMOUNT_INVALID")
    @NotNull(message = "AMOUNT_NOT_NULL")
    BigDecimal amount;
    String content;
}

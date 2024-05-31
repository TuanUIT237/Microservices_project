package com.tuan.ebankservice.dto.accountdto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.tuan.ebankservice.validator.BigDecimalLength;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

    @BigDecimalLength(min = 10000, message = "AMOUNT_INVALID")
    @NotNull(message = "AMOUNT_NOT_NULL")
    BigDecimal amount;

    String content;
}

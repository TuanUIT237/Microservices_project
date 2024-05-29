package com.tuan.ebankservice.dto.creditcarddto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditPaymentHistoryRequest {
    @NotBlank(message = "CREDIT_CARD_ID_NOT_NULL")
    String creditCardId;

    @NotNull(message = "START_DATE_NOT_NULL")
    LocalDate startDate;

    @NotNull(message = "END_DATE_NOT_NULL")
    LocalDate endDate;
}

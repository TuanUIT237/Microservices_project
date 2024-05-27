package com.tuan.ebankservice.dto.creditcarddto;

import jakarta.mail.event.MailEvent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardUpdateRequest {
    @NotBlank(message = "USERID_ACCOUNT_NOT_BLANK")
    String userId;
    @NotBlank(message = "CVV_ACCOUNT_NOT_BLANK")
    String cvv;
    LocalDate expireDate;
    @NotNull(message = "TOTAL_LIMIT_NOT_NULL")
    BigDecimal totalLimit;
    @NotNull(message = "AVAILABLE_LIMIT_NOT_NULL")
    BigDecimal availableLimit;
    @NotNull(message = "CURRENT_DEBT_NOT_NULL")
    BigDecimal currentDebt;
    @NotNull(message = "MINIMUM_PAYMENT_NOT_NULL")
    BigDecimal minimumPaymentAmount;
    Integer cutoffDate;
    LocalDate dueDate;
}

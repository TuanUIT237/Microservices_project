package com.tuan.ebankservice.dto.creditcarddto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tuan.ebankservice.entity.CreditCardPayment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardResponse {
    String id;
    String userId;
    String password;
    String cvv;
    LocalDate expireDate;
    BigDecimal totalLimit;
    BigDecimal availableLimit;
    BigDecimal currentDebt;
    BigDecimal minimumPaymentAmount;
    Integer cutoffDate;
    LocalDate dueDate;
    Set<CreditCardPayment> creditCardPayments;
}

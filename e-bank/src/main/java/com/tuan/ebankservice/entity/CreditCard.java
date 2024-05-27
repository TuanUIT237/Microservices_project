package com.tuan.ebankservice.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String userId;
    String cvv;
    LocalDate expireDate;
    BigDecimal totalLimit;
    BigDecimal availableLimit;
    BigDecimal currentDebt;
    BigDecimal minimumPaymentAmount;
    Integer cutoffDate;
    LocalDate dueDate;
    LocalDateTime cancelDate;
    String status;
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name = "credit_card_id")
    Set<CreditCardPayment> creditCardPayments;
}

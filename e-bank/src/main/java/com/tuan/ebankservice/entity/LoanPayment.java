package com.tuan.ebankservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    BigDecimal amount;

    @CreationTimestamp
    LocalDateTime paymentDate;

    String description;
}

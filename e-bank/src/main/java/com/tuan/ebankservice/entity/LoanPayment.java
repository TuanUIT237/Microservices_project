package com.tuan.ebankservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

package com.tuan.ebankservice.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


import java.math.BigDecimal;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    BigDecimal amount;
    @CreationTimestamp
    LocalDateTime paymentDate;
    String description;
    String paymentType;
}

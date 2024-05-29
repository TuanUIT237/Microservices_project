package com.tuan.ebankservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {
    @Id
    @GenericGenerator(name = "account_number_gen", strategy = "com.tuan.ebankservice.util.AccountNumberGenerator")
    @GeneratedValue(generator = "account_number_gen")
    String id;

    BigDecimal balance = BigDecimal.valueOf(0);
    String name;
    String status;
    String userId;

    @CreationTimestamp
    LocalDateTime createDate;

    LocalDateTime cancelDate;
}

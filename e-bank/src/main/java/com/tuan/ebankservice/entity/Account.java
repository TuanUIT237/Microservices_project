package com.tuan.ebankservice.entity;


import com.tuan.ebankservice.util.AccountStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
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
public class Account {
    @Id
    @GenericGenerator(name="account_number_gen", strategy = "com.tuan.ebankservice.util.AccountNumberGenerator")
    @GeneratedValue(generator = "account_number_gen")
    String id;
    BigDecimal balance = BigDecimal.valueOf(0);
    String name;
    String status;
    String userId;
    @CreationTimestamp
    LocalDateTime createDate;
    @CreationTimestamp
    LocalDateTime cancelDate;
}

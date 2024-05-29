package com.tuan.ebankservice.dto.accountdto;

import java.math.BigDecimal;

import com.tuan.ebankservice.util.AccountStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    String id;
    BigDecimal balance;
    String name;
    String userId;
    String password;
    AccountStatus status;
}

package com.tuan.ebankservice.dto.accountdto;

import com.tuan.ebankservice.util.AccountStatus;
import com.tuan.ebankservice.validator.BigDecimalLength;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

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

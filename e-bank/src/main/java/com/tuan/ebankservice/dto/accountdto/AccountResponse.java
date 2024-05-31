package com.tuan.ebankservice.dto.accountdto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tuan.ebankservice.util.AccountStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AccountResponse {
    String id;
    BigDecimal balance;
    String name;
    String userId;
    String password;
    AccountStatus status;
}

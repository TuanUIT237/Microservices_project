package com.tuan.ebankservice.dto.accountdto;

import com.tuan.ebankservice.validator.BigDecimalLength;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCreationRequest {
    String citizenIdCard;
    @Size(min = 5,message = "ACCOUNT_NAME_INVALID")
    String name;
    @BigDecimalLength(min = 100000, message = "ACCOUNT_BALANCE_INVALID")
    BigDecimal balance;
}

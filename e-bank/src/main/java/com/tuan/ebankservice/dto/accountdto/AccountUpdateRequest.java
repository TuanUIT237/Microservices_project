package com.tuan.ebankservice.dto.accountdto;

import com.tuan.ebankservice.util.AccountStatus;
import com.tuan.ebankservice.validator.BigDecimalLength;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountUpdateRequest {
    @BigDecimalLength(min = 1, message = "BALANCE_ACCOUNT_INVALID")
    BigDecimal balance;
    @NotBlank(message = "NAME_ACCOUNT_NOT_BLANK")
    String name;
    @NotBlank(message = "STATUS_ACCOUNT_NOT_BLANK")
    String status;
    @NotNull(message = "CANCEL_DATE_ACCOUNT_NOT_NULL")
    LocalDateTime cancelDate;
}

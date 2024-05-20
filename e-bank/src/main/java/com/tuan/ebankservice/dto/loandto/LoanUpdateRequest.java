package com.tuan.ebankservice.dto.loandto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanUpdateRequest {
    String id;
    BigDecimal remainingPrincipal;
    String status;
}

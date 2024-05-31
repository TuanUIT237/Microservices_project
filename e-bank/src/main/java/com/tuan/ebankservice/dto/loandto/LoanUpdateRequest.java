package com.tuan.ebankservice.dto.loandto;

import java.math.BigDecimal;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

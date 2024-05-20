package com.tuan.ebankservice.dto.userprofiledto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileGetUserIdRequest {
    String fullName;
    String citizenIdCard;
}

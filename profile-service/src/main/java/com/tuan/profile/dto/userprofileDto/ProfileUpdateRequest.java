package com.tuan.profile.dto.userprofileDto;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class ProfileUpdateRequest {
    String userId;
    String firstName;
    String email;
    String phone;
    String lastName;
    String citizenIdCard;
    LocalDate dob;
    String city;
}

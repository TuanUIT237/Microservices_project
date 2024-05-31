package com.tuan.identityservice.dto.ProfileDto;

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
    String lastName;
    String phone;
    String email;
    String citizenIdCard;
    LocalDate dob;
    String city;
}

package com.tuan.profile.dto.userprofileDto;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    String firstName;
    String email;
    String lastName;
    String citizenIdCard;
    LocalDate dob;
    String city;
}

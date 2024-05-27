package com.tuan.profile.dto.userprofileDto;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreationRequest {
    String userId;
    String firstName;
    String lastName;
    String email;
    String phone;
    String citizenIdCard;
    LocalDate dob;
    String city;
}

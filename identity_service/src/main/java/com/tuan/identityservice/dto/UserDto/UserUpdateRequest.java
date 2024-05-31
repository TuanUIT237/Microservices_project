package com.tuan.identityservice.dto.UserDto;

import java.time.LocalDate;

import jakarta.validation.constraints.Null;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    String userId;
    String password;
    String firstName;
    String lastName;
    String phone;
    String email;
    String citizenIdCard;
    LocalDate dob;
    String city;
}

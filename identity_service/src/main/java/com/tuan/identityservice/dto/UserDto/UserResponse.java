package com.tuan.identityservice.dto.UserDto;

import java.time.LocalDate;
import java.util.Set;

import com.tuan.identityservice.entity.Role;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    String phone;
    String email;
    String citizenIdCard;
    LocalDate dob;
    String city;
    Set<Role> roles;
}

package com.tuan.ebankservice.dto.userdto;

import java.time.LocalDate;

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
    String phone;
    String email;
    String lastName;
    String citizenIdCard;
    LocalDate dob;
}

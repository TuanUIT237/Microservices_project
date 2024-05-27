package com.tuan.ebankservice.dto.userdto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String username;
    String password;
    String email;
    String phone;
    String firstName;
    String lastName;
    String citizenIdCard;
    LocalDate dob;
}

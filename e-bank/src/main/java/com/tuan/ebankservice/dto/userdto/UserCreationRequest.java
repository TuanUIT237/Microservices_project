package com.tuan.ebankservice.dto.userdto;

import java.time.LocalDate;

import lombok.*;
import lombok.experimental.FieldDefaults;

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

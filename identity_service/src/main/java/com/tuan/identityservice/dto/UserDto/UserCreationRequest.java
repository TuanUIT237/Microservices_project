package com.tuan.identityservice.dto.UserDto;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.tuan.identityservice.validator.DobContraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @Size(min = 3, message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;
    String citizenIdCard;
    String email;

    @DobContraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    String city;
}

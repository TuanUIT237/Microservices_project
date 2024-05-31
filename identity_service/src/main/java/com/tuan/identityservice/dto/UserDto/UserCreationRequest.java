package com.tuan.identityservice.dto.UserDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.tuan.identityservice.validator.CitizenCardIdContraint;
import com.tuan.identityservice.validator.DobContraint;
import com.tuan.identityservice.validator.EmailContraint;
import com.tuan.identityservice.validator.PhoneContraint;

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

    @NotBlank(message = "CITIZEN_CARD_ID_NOT_BLANK")
    @CitizenCardIdContraint(message = "CITIZEN_CARD_ID_INVALID")
    String citizenIdCard;

    @EmailContraint(message = "EMAIL_INVALID")
    @NotBlank(message = "EMAIL_NOT_BLANK")
    String email;

    @PhoneContraint(message = "PHONE_INVALID")
    @NotBlank(message = "PHONE_NOT_BLANK")
    String phone;

    @DobContraint(min = 18, message = "INVALID_DOB")
    LocalDate dob;

    String city;
}

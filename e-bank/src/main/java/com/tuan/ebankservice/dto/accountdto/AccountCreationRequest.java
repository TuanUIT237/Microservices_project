package com.tuan.ebankservice.dto.accountdto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.tuan.ebankservice.validator.BigDecimalLength;
import com.tuan.ebankservice.validator.CitizenCardIdContraint;
import com.tuan.ebankservice.validator.EmailContraint;
import com.tuan.ebankservice.validator.PhoneContraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountCreationRequest {
    @NotBlank(message = "CITIZEN_CARD_ID_NOT_BLANK")
    @CitizenCardIdContraint(message = "CITIZEN_CARD_ID_INVALID")
    String citizenIdCard;

    @Size(min = 5, message = "ACCOUNT_NAME_INVALID")
    String name;

    @BigDecimalLength(min = 100000, message = "ACCOUNT_BALANCE_INVALID")
    BigDecimal balance;

    @PhoneContraint(message = "PHONE_INVALID")
    @NotBlank(message = "PHONE_NOT_BLANK")
    String phone;

    @EmailContraint(message = "EMAIL_INVALID")
    @NotBlank(message = "EMAIL_NOT_BLANK")
    String email;
}

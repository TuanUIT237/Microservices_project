package com.tuan.ebankservice.dto.loandto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

import com.tuan.ebankservice.util.LoanStatus;
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
public class LoanCreationRequest {
    @Size(min = 5, message = "ACCOUNT_NAME_INVALID")
    String name;

    @BigDecimalLength(min = 1000000, message = "PRINCIPAL_LOAN_AMOUNT_INVALID")
    @NotNull(message = "PRINCIPAL_LOAN_AMOUNT_NOT_NULL")
    BigDecimal principalLoanAmount;

    @Min(value = 1, message = "INSTALLMENT_COUNT_MIN_INVALID")
    @Max(value = 360, message = "INSTALLMENT_COUNT_MIN_INVALID")
    Integer installmentCount;

    @NotBlank(message = "CITIZEN_CARD_ID_NOT_BLANK")
    @CitizenCardIdContraint(message = "CITIZEN_CARD_ID_INVALID")
    String citizenIdCard;

    @PhoneContraint(message = "PHONE_INVALID")
    @NotBlank(message = "PHONE_NOT_BLANK")
    String phone;

    @EmailContraint(message = "EMAIL_INVALID")
    @NotBlank(message = "EMAIL_NOT_BLANK")
    String email;

    String status = LoanStatus.CONTINUING.name();
}

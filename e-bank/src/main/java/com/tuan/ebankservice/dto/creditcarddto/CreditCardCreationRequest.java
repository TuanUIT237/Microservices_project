package com.tuan.ebankservice.dto.creditcarddto;


import com.tuan.ebankservice.util.CreditCardStatus;
import com.tuan.ebankservice.util.StringUtil;
import com.tuan.ebankservice.validator.BigDecimalLength;

import com.tuan.ebankservice.validator.CitizenCardIdContraint;
import com.tuan.ebankservice.validator.EmailContraint;
import com.tuan.ebankservice.validator.PhoneContraint;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreditCardCreationRequest {
    @BigDecimalLength(min = 100000, message = "CREDIT_CARD_LIMIT_INVALID")
    @NotNull(message = "CREDIT_CARD_LIMIT_NOTNULL")
    BigDecimal limit;
    @Min(value = 1, message = "MIN_DAY_OF_CUTOFF_INVALID")
    @Max(value = 29, message = "MAX_DAY_OF_CUTOFF_INVALID")
    Integer dayOfCutOff;
    @NotBlank(message = "CITIZEN_CARD_ID_NOT_BLANK")
    @CitizenCardIdContraint(message = "CITIZEN_CARD_ID_INVALID")
    String citizenIdCard;
    @PhoneContraint(message = "PHONE_INVALID")
    @NotBlank(message = "PHONE_NOT_BLANK")
    String phone;
    @EmailContraint(message = "EMAIL_INVALID")
    @NotBlank(message = "EMAIL_NOT_BLANK")
    String email;
    @Size(min = 5,message = "ACCOUNT_NAME_INVALID")
    String name;
    @Builder.Default
    LocalDate expireDate = LocalDate.now().plusYears(5);
    @Builder.Default
    String cvv = StringUtil.getRandomNumberAsString(7);
    @Builder.Default
    LocalDate dueDate = LocalDate.now().plusMonths(36);
    @Builder.Default
    String status = CreditCardStatus.ACTIVE.toString();
}

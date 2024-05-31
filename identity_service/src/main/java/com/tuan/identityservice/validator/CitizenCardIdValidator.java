package com.tuan.identityservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CitizenCardIdValidator implements ConstraintValidator<CitizenCardIdContraint, String> {
    @Override
    public void initialize(CitizenCardIdContraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String citizenCardId, ConstraintValidatorContext constraintValidatorContext) {
        if (citizenCardId == null) {
            return false;
        }

        if (citizenCardId.matches("\\d{12}")) return true;
        else return false;
    }
}

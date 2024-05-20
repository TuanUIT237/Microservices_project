package com.tuan.ebankservice.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class BigDecimalLengthValidator implements ConstraintValidator<BigDecimalLength, BigDecimal> {
    private int min;

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        return value == null || value.intValue() >= min;
    }

    @Override
    public void initialize(BigDecimalLength constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }
}

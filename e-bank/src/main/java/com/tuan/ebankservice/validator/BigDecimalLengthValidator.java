package com.tuan.ebankservice.validator;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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

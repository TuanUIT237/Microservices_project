package com.tuan.ebankservice.validator;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneValidator.class})
public @interface PhoneContraint {
    String message() default "Invalid phone";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

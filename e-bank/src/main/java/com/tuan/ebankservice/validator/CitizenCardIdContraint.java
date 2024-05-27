package com.tuan.ebankservice.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CitizenCardIdValidator.class})
public @interface CitizenCardIdContraint {
    String message() default "Invalid citizen card id";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.portfolio.core.helpers.validators.annotations;

import com.portfolio.core.helpers.validators.validators.FutureDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Supports Date, LocalDate, LocalDateTime
 */
@Documented
@Constraint(validatedBy = FutureDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {
    String message() default "Date must be greater than today's date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
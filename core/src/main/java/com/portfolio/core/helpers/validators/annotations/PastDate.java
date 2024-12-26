package com.portfolio.core.helpers.validators.annotations;

import com.portfolio.core.helpers.validators.validators.PastDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Supports Date, LocalDate, LocalDateTime
 */
@Documented
@Constraint(validatedBy = PastDateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PastDate {
    String message() default "Date must be smaller than today's date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
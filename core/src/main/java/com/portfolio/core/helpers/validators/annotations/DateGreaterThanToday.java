package com.portfolio.core.helpers.validators.annotations;

import com.portfolio.core.helpers.validators.validators.DateGreaterThanTodayValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Supports Date, LocalDate, LocalDateTime
 */
@Documented
@Constraint(validatedBy = DateGreaterThanTodayValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateGreaterThanToday {
    String message() default "Date must be greater than today's date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
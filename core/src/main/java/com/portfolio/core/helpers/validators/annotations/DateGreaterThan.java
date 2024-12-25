package com.portfolio.core.helpers.validators.annotations;

import com.portfolio.core.helpers.validators.validators.DateGreaterThanValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Supports Date, LocalDate, LocalDateTime
 */
@Documented
@Repeatable(DateGreaterThan.List.class)
@Constraint(validatedBy = DateGreaterThanValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateGreaterThan {
    String message() default "{field} must be greater than {compareTo}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String target();
    String compareTo();

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE})
    @Documented
    @interface List {
        DateGreaterThan[] value();
    }
}
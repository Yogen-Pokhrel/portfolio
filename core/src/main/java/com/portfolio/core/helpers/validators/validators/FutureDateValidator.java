package com.portfolio.core.helpers.validators.validators;

import com.portfolio.core.helpers.validators.annotations.FutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class FutureDateValidator implements ConstraintValidator<FutureDate, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            //Not null should be validated by different constraint
            return true;
        }

        if (value instanceof Date) {
            return ((Date) value).after(new Date());
        } else if (value instanceof LocalDate) {
            return ((LocalDate) value).isAfter(LocalDate.now());
        } else if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).isAfter(LocalDateTime.now());
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Provided object is not of known type: [Date, LocalDate, LocalDateTime] Provided: " + value.getClass().getSimpleName()).addConstraintViolation();
        return false;
    }
}
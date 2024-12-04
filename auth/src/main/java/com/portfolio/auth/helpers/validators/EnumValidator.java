package com.portfolio.auth.helpers.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Enum<?>[] enumValues;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        this.enumValues = enumClass.getEnumConstants();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Allow null; use @NotNull or @NotBlank for non-null constraints
        }

        // Check if the value is part of the enum
        return Arrays.stream(enumValues)
                .anyMatch(enumValue -> enumValue.equals(value));
    }
}

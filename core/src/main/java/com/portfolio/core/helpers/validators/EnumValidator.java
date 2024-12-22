package com.portfolio.core.helpers.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;


public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Enum<?>[] enumValues;
    private boolean required;
    private String messageTemplate;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        this.enumValues = enumClass.getEnumConstants();
        this.required = constraintAnnotation.required();
        this.messageTemplate = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null && !required) {
            createConstraintViolationMessage(context);
            return false;
        }

        boolean isValid = Arrays.asList(enumValues).contains(value);
        if (!isValid) {
            createConstraintViolationMessage(context);
        }
        return isValid;

    }

    private void createConstraintViolationMessage(ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        String enumValuesString = Arrays.stream(enumValues)
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        String finalMessage = messageTemplate.contains("{values}")
                ? messageTemplate.replace("{values}", enumValuesString)
                : messageTemplate;

        context.buildConstraintViolationWithTemplate(finalMessage)
                .addConstraintViolation();
    }
}

package com.portfolio.core.helpers.validators.validators;

import com.portfolio.core.helpers.validators.annotations.RequiredIf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class RequiredIfValidator implements ConstraintValidator<RequiredIf, Object> {

    private String[] targetFields;
    private String dependsOnField;
    private String requiredValue;
    private String message;

    @Override
    public void initialize(RequiredIf constraintAnnotation) {
        this.targetFields = constraintAnnotation.target();
        this.dependsOnField = constraintAnnotation.dependsOn();
        this.requiredValue = constraintAnnotation.requiredWhenValue();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            // Get the value of the dependsOn field.
            Field dependsOnField = value.getClass().getDeclaredField(this.dependsOnField);
            dependsOnField.setAccessible(true);
            Object dependsOnValue = dependsOnField.get(value);

            // If dependsOnValue doesn't match the required value, validation passes.
            if (dependsOnValue == null || !dependsOnValue.toString().equals(this.requiredValue)) {
                return true;
            }

            // Check if target fields are null.
            boolean validationFlag = true;
            for (String targetField : this.targetFields) {
                Field target = value.getClass().getDeclaredField(targetField);
                target.setAccessible(true);
                Object targetValue = target.get(value);

                if (targetValue == null) {
                    // If any target field is null, validation fails.
                    String msg = message.isEmpty() ? (createMessage(targetField, dependsOnField.getName(), this.requiredValue)) : message;

                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate(msg)
                            .addPropertyNode(targetField)
                            .addConstraintViolation();

                    validationFlag = false;

                    //instead of early termination all the fields are iterated such that api can display all the errors for the given data in single response
                    //return false
                }
            }
            return validationFlag;

        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Error accessing fields during validation {}, No such field or illegal access", e.getMessage());
        }catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
        return false;
    }

    private String createMessage(String targetField, String dependsOnField, String requiredValue) {
        return targetField + " cannot be null when " + dependsOnField + " is set to " + requiredValue;
    }
}


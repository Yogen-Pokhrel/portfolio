package com.portfolio.core.helpers.validators.validators;

import com.portfolio.core.helpers.validators.annotations.ValidateIf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Set;

@Slf4j
public class ValidateIfValidator implements ConstraintValidator<ValidateIf, Object> {

    private String targetField;
    private String dependsOnField;
    private String requiredValue;
    private String message;

    @Override
    public void initialize(ValidateIf constraintAnnotation) {
        this.targetField = constraintAnnotation.target();
        this.dependsOnField = constraintAnnotation.dependsOn();
        this.requiredValue = constraintAnnotation.triggerWhenValue();
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

            Field targetField = value.getClass().getDeclaredField(this.targetField);
            targetField.setAccessible(true);
            Object targetValue = targetField.get(value);

            // If target field is null, validation fails
            if (targetValue == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(createMessage())
                        .addPropertyNode(this.targetField)
                        .addConstraintViolation();
                return false;
            }

            Set<ConstraintViolation<Object>> violations = Validation.buildDefaultValidatorFactory()
                    .getValidator()
                    .validate(targetValue);

            if (!violations.isEmpty()) {
                context.disableDefaultConstraintViolation();
                for (ConstraintViolation<Object> violation : violations) {
                    context.buildConstraintViolationWithTemplate(violation.getMessage())
                            .addPropertyNode(this.targetField + "." + violation.getPropertyPath().toString())
                            .addConstraintViolation();
                }
                return false;
            }
            return true;

        }catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("Error, No such Field or Illegal Access: {}", e.getMessage());
        }catch (Exception e) {
            log.error("Exception: {}", e.getMessage());
        }
        return false;
    }

    private String createMessage() {
        return message.isEmpty() ? (targetField + " cannot be null when " + dependsOnField + " is set to " + requiredValue) : message;
    }
}


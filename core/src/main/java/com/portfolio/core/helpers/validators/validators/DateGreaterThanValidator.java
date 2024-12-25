package com.portfolio.core.helpers.validators.validators;

import com.portfolio.core.helpers.validators.annotations.DateGreaterThan;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public class DateGreaterThanValidator implements ConstraintValidator<DateGreaterThan, Object> {
    private String compareTo;
    private String target;
    private String message;

    @Override
    public void initialize(DateGreaterThan constraintAnnotation) {
        compareTo = constraintAnnotation.compareTo();
        target = constraintAnnotation.target();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
            Object field = wrapper.getPropertyValue(this.target);
            Object compareTo = wrapper.getPropertyValue(this.compareTo);

            prepareMessage(context);

            if (field instanceof Date && compareTo instanceof Date) {
                return ((Date) field).after((Date) compareTo);
            } else if (field instanceof LocalDate && compareTo instanceof LocalDate) {
                return ((LocalDate) field).isAfter((LocalDate) compareTo);
            } else if (field instanceof LocalDateTime && compareTo instanceof LocalDateTime) {
                return ((LocalDateTime) field).isAfter((LocalDateTime) compareTo);
            }

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Provided object is not of known type: [Date, LocalDate, LocalDateTime] Provided: ").addConstraintViolation();
            log.error("Provided object is not of known type: [Date, LocalDate, LocalDateTime] Provided: ");
            return false;
        } catch (Exception e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Error occurred while comparing objects. One or more of the provided fields [" + this.target + ", " + this.compareTo + "] could not be found" ).addConstraintViolation();
            log.error("Error occurred while comparing object: {}", e.getMessage());
            return false;
        }
    }

    private void prepareMessage(ConstraintValidatorContext context){
        context.disableDefaultConstraintViolation();
        message = this.message.replace("{field}", target).replace("{compareTo}", compareTo);
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
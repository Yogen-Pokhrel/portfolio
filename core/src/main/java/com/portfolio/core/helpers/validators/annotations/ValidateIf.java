package com.portfolio.core.helpers.validators.annotations;

import com.portfolio.core.helpers.validators.validators.ValidateIfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidateIfValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidateIf.List.class)
public @interface ValidateIf {
    String target();
    String dependsOn();
    String triggerWhenValue();
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        ValidateIf[] value();
    }
}

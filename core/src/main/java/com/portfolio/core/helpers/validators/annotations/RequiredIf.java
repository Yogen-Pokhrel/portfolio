package com.portfolio.core.helpers.validators.annotations;

import com.portfolio.core.helpers.validators.validators.RequiredIfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RequiredIfValidator.class)
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(RequiredIf.List.class)
public @interface RequiredIf {
    String[] target();
    String dependsOn();
    String requiredWhenValue();
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        RequiredIf[] value();
    }
}

package com.huce.edu.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BooleanValueValidator.class)
public @interface BooleanValue {
    String message() default "Invalid boolean value. Must be true or false";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
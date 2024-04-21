package com.huce.edu.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BooleanValueValidator implements ConstraintValidator<BooleanValue, Boolean> {
    @Override
    public void initialize(BooleanValue constraintAnnotation) {
    }

    @Override
    public boolean isValid(Boolean value, ConstraintValidatorContext context) {
        return value != null; // Đảm bảo giá trị không phải null (true hoặc false).
    }
}

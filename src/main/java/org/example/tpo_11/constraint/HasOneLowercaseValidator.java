package org.example.tpo_11.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HasOneLowercaseValidator implements ConstraintValidator<HasOneLowercase, String> {
    @Override
    public void initialize(HasOneLowercase constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.matches(".*[a-z].*");

    }
}

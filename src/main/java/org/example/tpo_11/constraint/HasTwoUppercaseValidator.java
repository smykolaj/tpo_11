package org.example.tpo_11.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HasTwoUppercaseValidator implements ConstraintValidator<HasTwoUppercase, String> {
    @Override
    public void initialize(HasTwoUppercase constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.matches(".*[A-Z].{0,}[A-Z].*");
    }
}

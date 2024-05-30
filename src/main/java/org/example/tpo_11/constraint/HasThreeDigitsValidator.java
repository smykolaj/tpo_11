package org.example.tpo_11.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HasThreeDigitsValidator implements ConstraintValidator<HasThreeDigits, String> {
    @Override
    public void initialize(HasThreeDigits constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.matches(".*(\\d.*){3}");

    }
}

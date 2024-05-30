package org.example.tpo_11.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HasFourSpecialsValidator implements ConstraintValidator<HasFourSpecials, String> {
    @Override
    public void initialize(HasFourSpecials constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s == null || s.matches(".*([\\W_].*){4}");

    }
}

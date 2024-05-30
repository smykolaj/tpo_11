package org.example.tpo_11.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TargetUrlIsHttpsValidator implements ConstraintValidator<TargetUrlIsHttps, String> {

    @Override
    public void initialize(TargetUrlIsHttps constraint) {
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        return url.startsWith("https://");
    }
}

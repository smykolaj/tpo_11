package org.example.tpo_11.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = HasOneLowercaseValidator.class)
@Target( ElementType.FIELD )
@Retention(RetentionPolicy.RUNTIME)
public @interface HasOneLowercase {
    String message() default "{org.example.tpo_11.NoOneLowercase.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

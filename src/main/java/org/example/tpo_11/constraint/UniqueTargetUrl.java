package org.example.tpo_11.constraint;


import jakarta.validation.*;
import java.lang.annotation.*;

@Constraint(validatedBy = UniqueTargetUrlValidator.class)
@Target( ElementType.FIELD )
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTargetUrl {
    String message() default "{org.example.tpo_11.UrlIsNotUnique.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
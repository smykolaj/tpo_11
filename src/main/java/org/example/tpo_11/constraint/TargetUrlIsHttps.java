package org.example.tpo_11.constraint;


import jakarta.validation.*;
import java.lang.annotation.*;

@Constraint(validatedBy = TargetUrlIsHttpsValidator.class)
@Target( ElementType.FIELD )
@Retention(RetentionPolicy.RUNTIME)
public @interface TargetUrlIsHttps {
        String message() default "{org.example.tpo_11.UrlIsNotHttps.message}";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};
}

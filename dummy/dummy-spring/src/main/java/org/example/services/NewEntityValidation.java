package org.example.services;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = { NewEntityValidator.class })
public @interface NewEntityValidation {

    String message () default "";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};

    Class<?>[] thisClass() default {};
}

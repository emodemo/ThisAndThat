package org.example.services;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = { ExistingEntityValidation.ExistingEntityValidator.class })
public @interface ExistingEntityValidation {

    String message () default "";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};

    @RequiredArgsConstructor
    class ExistingEntityValidator implements ConstraintValidator<ExistingEntityValidation, Whatever> {

        private final EmailService emailService;


        @Override
        public void initialize(ExistingEntityValidation constraintAnnotation) {
            for (Class<?> group : constraintAnnotation.groups()) {
                group.getName().equals("whatever");
            }

        }

        @Override
        public boolean isValid(Whatever value, ConstraintValidatorContext context) {
            emailService.send(value.getSomething(), value.getSomething());
            replaceMessage(value, context);
            return true;
        }

        private void replaceMessage(Whatever value, ConstraintValidatorContext context){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Whatever with value %s already exists".formatted(value.getSomething())).addConstraintViolation();
        }
    }

}

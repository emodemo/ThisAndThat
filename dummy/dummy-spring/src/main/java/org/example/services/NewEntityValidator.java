package org.example.services;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class NewEntityValidator implements ConstraintValidator<NewEntityValidation, Object> {

    @Override
    public void initialize(NewEntityValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        return true;
    }
}

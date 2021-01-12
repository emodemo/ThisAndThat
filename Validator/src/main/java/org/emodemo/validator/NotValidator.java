package org.emodemo.validator;

public class NotValidator implements IValidator{

    private IValidator validator;

    public NotValidator(IValidator validator) {
        this.validator = validator;
    }

    @Override
    public boolean validate(Person person) {
        return !validator.validate(person);
    }
}

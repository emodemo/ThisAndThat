package org.emodemo.validator2;

import java.util.ArrayList;
import java.util.List;

class AndValidator implements IValidator {

    private List<IValidator> validators;

    public AndValidator(List<IValidator> validators) {
        this.validators = new ArrayList<>(validators);
    }

    @Override
    public boolean validate(Person person) {
        for(IValidator validator : validators){
            if(!validator.validate(person)){
                return false;
            }
        }
        return true;
    }
}

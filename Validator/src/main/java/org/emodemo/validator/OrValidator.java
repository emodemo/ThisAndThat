package org.emodemo.validator;

import java.util.ArrayList;
import java.util.List;

public class OrValidator implements IValidator{

    private List<IValidator> validators;

    public OrValidator(List<IValidator> validators) {
        this.validators = new ArrayList<>(validators);
    }

    @Override
    public boolean validate(Person person) {
        for(IValidator validator : validators){
            if(validator.validate(person)){
                return true;
            }
        }
        return false;
    }
}

package org.emodemo.validator2;

import static java.util.Arrays.asList;

@FunctionalInterface
public interface IValidator{

    boolean validate(Person person);
    
    static IValidator not(IValidator validator) {
    	return new NotValidator(validator);
    }

    static IValidator and(IValidator ... validators) {
    	return new AndValidator(asList(validators));
    }
    
    static IValidator or(IValidator ... validators) {
    	return new OrValidator(asList(validators));
    }
    
    
}

package org.emodemo.validator;

public class AgeValidator implements IValidator {

    private int age;

    public AgeValidator(int age) {
        this.age = age;
    }

    @Override
    public boolean validate(Person person) {
        return person.getAge() == age;
    }
}

package org.emodemo.validator2;

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

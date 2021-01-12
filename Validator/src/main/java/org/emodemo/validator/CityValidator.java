package org.emodemo.validator;

public class CityValidator implements IValidator {

    private String city;

    public CityValidator(String city) {
        this.city = city;
    }

    @Override
    public boolean validate(Person person) {
        return person.getCity().equals(city);
    }
}

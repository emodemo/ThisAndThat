package org.emodemo.validator;

public class CountryValidator implements IValidator {

    private String country;

    public CountryValidator(String country) {
        this.country = country;
    }

    @Override
    public boolean validate(Person person) {
        return person.getCountry().equals(country);
    }
}

package org.emodemo.validator2;

import static org.emodemo.validator2.IValidator.and;
import static org.emodemo.validator2.IValidator.not;
import static org.emodemo.validator2.IValidator.or;
import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {

    private final Person p1 = new Person("France", "Paris", 30);
    private final Person p2 = new Person("Italy", "Milan", 33);
    private final Person p3 = new Person("France", "Lille", 33);
    private final Person p4 = new Person("Germany", "Munich", 33);
    // countries
    private final IValidator france = new CountryValidator("France");
    private final IValidator germany = new CountryValidator("Germany");
    // cities
    private final IValidator paris = new CityValidator("Paris");
    private final IValidator munich = new CityValidator("Munich");
    // ages
    private final IValidator age33 = new AgeValidator(33);
    private final IValidator age30 = new AgeValidator(30);

    @Test
    public void country() {
        assertTrue(france.validate(p1));
        assertFalse(germany.validate(p1));
    }

    @Test
    public void city() {
        assertTrue(paris.validate(p1));
        assertFalse(munich.validate(p1));
    }

    @Test
    public void age(){
        assertTrue(age30.validate(p1));
        assertFalse(age33.validate(p1));
    }

    @Test
    public void andTest(){
        IValidator franceAndParis = and(france, paris);
        assertTrue(franceAndParis.validate(p1));
        assertFalse(franceAndParis.validate(p2));

        IValidator franceParis30 = and(france, paris, age30);
        assertTrue(franceParis30.validate(p1));
        assertFalse(franceParis30.validate(p2));
    }

    @Test
    public void orTest(){
        IValidator franceOrGermany = or(france, germany);
        assertTrue(franceOrGermany.validate(p1));
        assertFalse(franceOrGermany.validate(p2));
        assertTrue(franceOrGermany.validate(p4));
    }

    @Test
    public void notTest(){
        IValidator notFrance = not(france);
        assertFalse(notFrance.validate(p1));
        assertTrue(notFrance.validate(p2));
    }

    @Test
    public void neitherAndTest(){
        // either france or germany, and is not 30 ages old
        IValidator validator = and(or(france, germany), not(age30));

        assertFalse(validator.validate(p1));
        assertFalse(validator.validate(p2));
        assertTrue(validator.validate(p3));
        assertTrue(validator.validate(p4));
    }
}
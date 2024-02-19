package org.emodemo.validator;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;

import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PersonTest {

    private final Person p1 = new Person("France", "Paris", 30);
    private final Person p2 = new Person("Italy", "Milan", 33);
    private final Person p3 = new Person("France", "Lille", 33);
    private final Person p4 = new Person("Germany", "Munich", 33);
    // countries
    private final IValidator france = new CountryValidator("France");
    private final IValidator germany = new CountryValidator("Germany");
    // countries without IValidatorApi
    private final Predicate<Person> franceP = person -> person.getCountry().equals("France");
    // cities
    private final IValidator paris = new CityValidator("Paris");
    private final IValidator munich = new CityValidator("Munich");
    // ages
    private final IValidator age33 = new AgeValidator(36);
    private final IValidator age30 = new AgeValidator(30);

    @Test
    public void country() {
        assertTrue(france.validate(p1));
        assertFalse(germany.validate(p1));

        // as a predicate
        assertTrue(franceP.test(p1));
        assertFalse(germany.test(p1));
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
    public void and(){
        IValidator franceAndParis = new AndValidator(asList(france, paris));
        assertTrue(franceAndParis.validate(p1));
        assertFalse(franceAndParis.validate(p2));

        // as a predicate
        Predicate<Person> and = france.and(paris);
        assertTrue(and.test(p1));
        assertFalse(and.test(p2));
    }

    @Test
    public void or(){
        IValidator franceOrGermany = new OrValidator(asList(france,germany));
        assertTrue(franceOrGermany.validate(p1));
        assertFalse(franceOrGermany.validate(p2));
        assertTrue(franceOrGermany.validate(p4));

        // as a predicate
        Predicate<Person> or = france.or(germany);
        assertTrue(or.test(p1));
        assertFalse(or.test(p2));
        assertTrue(or.test(p4));
    }

    @Test
    public void not(){
        IValidator notFrance = new NotValidator(france);
        assertFalse(notFrance.validate(p1));
        assertTrue(notFrance.validate(p2));

        // as a predicate
        Predicate<Person> not = france.negate();
        assertFalse(not.test(p1));
        assertTrue(not.test(p2));
    }

    @Test
    public void neitherAnd(){
        // either france or germany, and is not 30 ages old
        IValidator franceOrGermany = new OrValidator(asList(france,germany));
        IValidator not30 = new NotValidator(age30);
        IValidator validator = new AndValidator(asList(franceOrGermany, not30));
        assertFalse(validator.validate(p1));
        assertFalse(validator.validate(p2));
        assertTrue(validator.validate(p3));
        assertTrue(validator.validate(p4));

        // as a predicate
        Predicate<Person> neitherAnd = france.or(germany).and(age30.negate());
        assertFalse(neitherAnd.test(p1));
        assertFalse(neitherAnd.test(p2));
        assertTrue(neitherAnd.test(p3));
        assertTrue(neitherAnd.test(p4));

        // as stream
        List<Person> filtered = Stream.of(p1,p2,p3,p4).filter(neitherAnd).toList();
        assertEquals(2, filtered.size());
        assertTrue(filtered.contains(p3));
        assertTrue(filtered.contains(p4));

        // as stream v2
        List<Person> filtered_v2 = Stream.of(p1,p2,p3,p4).filter(validator).toList();
        assertEquals(2, filtered_v2.size());
        assertTrue(filtered_v2.contains(p3));
        assertTrue(filtered_v2.contains(p4));
    }
}
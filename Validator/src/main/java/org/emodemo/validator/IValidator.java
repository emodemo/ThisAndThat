package org.emodemo.validator;

import java.util.function.Predicate;

/**
 * A bit of a mix between validator and predicate.
 * Usage could be misleading, as it is not clear that validate() and test() do the same thing.
 * The useful part is that Not/And/Or implementation could be skipped.
 * The other useful part is that API is easier to use - see PersonTest.
 */
@FunctionalInterface
public interface IValidator extends Predicate<Person>{

    boolean validate(Person person);

    @Override
    default boolean test(Person person){
        return validate(person);
    }
}

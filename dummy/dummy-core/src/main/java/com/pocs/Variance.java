package com.pocs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/** Covaraince and Contravariance are about the hierarchy of wrapping types (Box, List, ...) */
public class Variance {

    public static void main(String[] args) {
        var cat = new Cat("Felix");
        var dog = new Dog("Rex");
        var mouse = new Mouse("Jerry", "Merry");
        var animal = new Cat("Tom");
        List<Cat> cats = List.of(cat);
        printNames(cats);
        compare(new AnimalComparator());
        // compare(new MouseComparator()); // will not compile

        // // this is not the intended usage, see print names
        List<Animal> animals = new ArrayList<>(List.of(cat, dog, animal));
        List<? extends Animal> animals2 = List.of(cat, dog, animal);
        List<? super Cat> animals3 = List.of(cat, dog, animal, mouse); // not nice as dog and mouse enter too
        animals3.forEach(System.out::println);
        animals.add(dog);
        // animals2.add(dog);
        // animals3.add(dog);
    }

    /**
     * Covariance input parameter, where effectively List<Cat> is a subtype of List<Animal>
     * wouldn't work if it was just invariant List<Animal> animals,
     * so we can READ all items from the list by: Animal a = list.get(0)
     * but we cannot WRITE into the list: list.add(cat) will fail
     */
    private static void printNames(List<? extends Animal> animals){
        // animals.add(new Cat("Melix")); // NO WRITES
        animals.forEach(animal -> System.out.println(animal.name()));
    }

    /**
     * Contravriance input parameter, where effectively Comparator<Animal> is a subtype of Comparator<Cat>
     * wouldn't work if it was just invariant Comparator<Cat> comparator,
     * so we can WRITE any item into the comparator: animalComparator.compare(cat1, cat2)
     * but we cannot READ the elements inside
     */
    private static int compare(Comparator<? super Cat> comparator){
        Cat felix = new Cat("Felix");
        Cat tom = new Cat("Tom");
        return comparator.compare(felix, tom);
    }

    static class AnimalComparator implements Comparator<Animal>{
        @Override
        public int compare(Animal animalA, Animal animalB) {
            return animalA.name().compareTo(animalB.name());
        }
    }

    static class MouseComparator implements Comparator<Mouse>{
        @Override
        public int compare(Mouse mouseA, Mouse mouseB) {
            return mouseA.name().compareTo(mouseB.name());
        }
    }
}

interface MyFuture<T> {
    /**
     * Given that Cat < Animal and Bus < Vehicle
     * then {@code Function<Animal, Bus> is a subtype of Function<Cat, Vehicle>}
     * so when Function<Cat, Vehicle> is expected Function<Animal, Bus> can be passed
     */
    <U> MyFuture<U> map(Function<? super T, ? extends U> f);
}



interface Animal {
    String name();
}

record Cat(String name) implements Animal {}
record Dog(String name) implements Animal {}
record Mouse(String name, String parent) {}

record Box<T> (T content) {}
record InvariantBox<T>(T animal) {}
/** if A is a subtype of B, then CovariantBox[A] is a subtype of CovariantBox[B]. */
record CovariantBox<T extends Animal>(T animal) {}
// if A is a subtype of B, then ContravariantBox[B] is a subtype of ContravariantBox[A].
// record ContravariantBox<T super Animal>(T animal) {} // doesn't compile
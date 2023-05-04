package com.pocs;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

public class Generics {

	public static void main(String[] args) {
		Optional<ClassAC> zero = getZero(asList(new ClassAC(), new ClassAC()));
		Optional<? extends Ia> zeroA = getZero(asList(new ClassAC(), new ClassAC2()));
		Optional<? extends Ic> zeroC = getZero(asList(new ClassAC(), new ClassAC2()));
		//getZero(asList(new ClassAB(), new ClassAB())); // compile time error
		zero.toString();

		Optional<Gen<Ia>> first = getFirst(asList(new Gen<>(new ClassAB()), new Gen<>(new ClassAC())));
		first.toString();

	}

	private static <T extends Ia & Ic> Optional<T> getZero(List<T> list){
		return Optional.ofNullable(list.get(0));
	}

	private static <T, U extends Gen<T>> Optional<U> getFirst(List<U> list){
		return Optional.ofNullable(list.get(0));
	}

	private void covaraince(List<? extends ClassB> list){
		ClassA a = new ClassA();
		ClassB b = new ClassB();
		ClassC c = new ClassC();
		a = list.get(0);
	}

	private void contravaraince(List<? super ClassB> list){
		ClassA a = new ClassA();
		ClassB b = new ClassB();
		ClassC c = new ClassC();
		list.add(c);
	}

	// producer extends (src, read from), consumer super (dest, add to)
	private static <T> void copy(List<? extends T> src, List<? super T> dest) {
		for (int i = 0; i < src.size(); i++)
			dest.set(i, src.get(i));
	}

}

interface Ia {}
interface Ib {}
interface Ic {}

class ClassAB implements Ia, Ib {}
class ClassAC implements Ia, Ic {}
class ClassAC2 implements Ia, Ic {}
class ClassA {}
class ClassB extends ClassA {}
class ClassC extends ClassB {}

class Gen<T> {
	private T t;

	public Gen(T t) {
		this.t = t;
	}

	T getT(){
		return t;
	}
}

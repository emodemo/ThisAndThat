package org.emodemo.cats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class FunctionalProgrammerToolkit {

	public static void main(String[] args) {

		Maybe<String> maybe = new Maybe<>("abcd");
		Maybe<Integer> map = maybe.map(String::length);
		Maybe<Integer> empty = map.empty();
		Maybe<Integer> ap = maybe.ap(new Maybe<>(String::length));

		// ============================================
		List<Function<Integer, Integer>> functions = new ArrayList<>();
		functions.add(i -> i + 1);
		functions.add(i -> i - 1);
		List<Integer> integers = List.of(1, 2);

		// Cross product. Create a partially applied function, apply the elements once
		// then fully apply the produced function with the elements once
		// List<Integer> ap1 = ap(functions, integers);
		// cross product
		BiFunction<Integer, Integer, Tuple> tuplef = (Integer a, Integer b) -> new Tuple(a, b);
		Function<Integer, Function<Integer, Tuple>> curryf = curry(tuplef);

		// step 1, apply the curry on each integer => create list(curry(1), curry(2))
		List<Function<Integer, Tuple>> listOfCurriedFunctions = ap(List.of(curryf), integers);
		// step 2, apply the curry(1) and curry(2) on each integer
		List<Tuple> ap2 = ap(listOfCurriedFunctions, integers);
		System.out.println(Arrays.toString(ap2.toArray()));


		// ============================================
		List<Line> line = List.of(
				new Line(2, 19.98),
				new Line(1, 1.99),
				new Line(3, 3.99));
		Optional<Line> reduce = line.stream().reduce(combine()); // line(6, 25.96)
	}

	// BiFunction
	static BinaryOperator<Line> combine() {
		return (line1, line2) -> {
			var qty = line1.qty + line2.qty;
			var total = line1.total + line2.total;
			return new Line(qty, total);
		};
	}
	record Line(int qty, double total){}

	//======================================

	// Applicative example 1 (i+1, i-1)(1,2) => (2,3,0,1)
	// could use some parallelization
	static <E, F> List<F> ap(List<Function<E, F>> functions, List<E> input) {
		java.util.List<F> result = new ArrayList<>();
		for (Function<E, F> f : functions) {
			for (E e : input) {
				F apply = f.apply(e);
				result.add(apply);
			}
		}
		return new ArrayList<>(result);
	}

	public static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> biFunction){
		System.out.println("outer" + biFunction.toString());
		//return a -> apply(biFunction, a);
		return a -> b -> biFunction.apply(a, b);
	}

	public static <A, B, C> Function<B, C> apply(BiFunction<A, B, C> biFunction, A a){
		System.out.println("inner " + biFunction.toString());
		return b-> biFunction.apply(a, b);
	}

	record Tuple(Integer a, Integer b){ }

	static void functionComposition(){
		// outside the tutorial
		Function<Double, Double> log = (value) -> Math.log(value);
		Function<Double, Double> logV2 = Math::log;
		Function<Double, Double> sqrt = (value) -> Math.sqrt(value);
		Function<Double, Double> sqrtv2 = Math::sqrt;
		Function<Double, Double> andThen = log.andThen(sqrt);
		Function<Double, Double> compose = sqrtv2.compose(logV2);
		assert andThen.equals(compose);
		assert andThen == compose;
		// =========================================================
		// currying
		Function<Double, Function<Double, Double>> weight = gravity -> mass -> {
			System.out.println(mass);
			return mass * gravity;
		};

		Function<Double, Double> weightOnEarth = weight.apply(9.81);
		weightOnEarth.apply(3.0);

		Function<Double, Function<Double, Double>> weightOnEarthV0 = a -> weight.apply(a);
		Function<Double, Double> apply = weightOnEarthV0.apply(9.81);

	}

	static Function<Double, Double> weightOnEarthV2() {
		final double gravity = 9.81;
		return mass -> mass * gravity;
	}
}

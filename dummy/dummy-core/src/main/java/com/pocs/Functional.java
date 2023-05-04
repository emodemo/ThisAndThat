package com.pocs;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Functional {

	public static void main(String[] args) {
		List<Function<Integer, Integer>> list = Arrays.asList(x -> x+1, x -> x+2, x -> x+3);
		int a = 0;
		Function<Integer, Integer> function = list.stream().reduce((f1, f2) -> f1.compose(f2)).get();
		Integer integer = function.apply(0);
		System.out.println(integer);

		Function<Integer, Integer> adderOld = partial((Integer x, Integer y) -> add(x, y), 6);
		Function<Integer, Integer> adder = partial((Integer x, Integer y) -> add(x, y), 5);
		System.out.println(adder.apply(2));
		BiFunction<Integer, Integer, Integer> minus = (x, y) -> x -y;
		Function<Integer, Integer> subtract = partial(minus, 5);
		System.out.println(subtract.apply(2));

		Function<Integer, Integer> sum = sum(1);
		System.out.println(sum.apply(2));

	}

	static int add(int x, int y){
		return x+y;
	}

	static <T, U, R> Function<U, R> partial(BiFunction<T, U, R> f, T x){
		return y -> f.apply(x, y);
	}

	static Function<Integer, Integer> sum(Integer x){
		return y -> y + x;
	}

}

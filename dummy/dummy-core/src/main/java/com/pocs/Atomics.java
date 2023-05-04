package com.pocs;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Atomics {

	public static void main(String[] args) {

	mutableClosureIsBAD();


	}



	private static void mutableClosureIsBAD(){

		List<Integer> numbers = Arrays.asList(1,2,3);
		int[] factor = new int[] {2};
		Stream<Integer> stream = numbers.stream().map(e -> e * factor[0]);
		factor[0] = 0;
		stream.forEach(Atomics::printMe);
	}

	private static void printMe(int i){
		System.out.println(i);
	}


	private static void onlyPositiveState(){

		int newValue = -13;
		AtomicInteger i = new AtomicInteger(10);
		IntBinaryOperator operator = (previous, next) -> previous + next < 0 ? 0 : previous + next;
		int i1 = i.accumulateAndGet(newValue, operator);
		System.out.println(i1);

	}

}

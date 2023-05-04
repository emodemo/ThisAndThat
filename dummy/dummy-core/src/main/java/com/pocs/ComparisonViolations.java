package com.pocs;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Interview {

	public static void main(String[] args) {
		violatedComparison2();

	}

	/**
	 * running this results in "Comparison method violates its general contract!"
	 * the stack trace leads to the comparator while the bug is in the code
	 */
	private static void violatedComparison(){
		var random = new Random(209);
		var ints = IntStream.range(0, 32)
				.mapToObj(index -> random.nextInt())
				.toList();

		List<Integer> integers = ints.stream()
				.sorted((i1, i2) -> i1 - i2)
				.toList();

		System.out.println(integers);
	}

	/**
	 * Another failure :)
	 * running this results in "Comparison method violates its general contract!"
	 * the stack trace leads to the comparator while the bug is in the code.
	 * Seeds and ranges re carefully chosen
	 */
	private static void violatedComparison2(){
		var random = new Random(2664);
		var ints = IntStream.range(0, 32)
				.mapToObj(index -> random.nextInt(1000, 1100))
				.toList();

		List<Integer> integers = ints.stream()
				.sorted((i1, i2) -> i1 < i2 ? -1 : i1==i2? 0 : 1) // the issue is in the ==, as these are objects
				.toList();

		System.out.println(integers);
	}





}

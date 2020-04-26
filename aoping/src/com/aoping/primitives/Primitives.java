package com.aoping.primitives;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Primitives {

	// see also Guava : com.google.common.primitives
	// see also it.unimi.dsi : fastutil    IntArrayList list = new IntArrayList(primitives);
	// see also net.sf.trove4j : trove4j   TIntList tList = new TIntArrayList();
	
	public static void main(String[] args) {
		
		// working directly with primitives
		// no autoboxing is required
		int[] primitives = {1,2,4,5,6,-8};
		IntStream.of(primitives).filter(i -> i > 1).count();
		String collect = IntStream.range(0, 50)
			.filter(i -> i < 10)
			.mapToObj(i -> String.valueOf(i)) // (String::valueOf)
			.collect(Collectors.joining(", ", "[", "]"));
		System.out.println(collect);
		
	}
	
}

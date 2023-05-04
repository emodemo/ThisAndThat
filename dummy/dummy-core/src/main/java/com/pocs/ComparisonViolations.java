package com.pocs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.math.RoundingMode.HALF_UP;

/**
 * Comparator is about total ordering. And total ordering requires:
 * transitivity: if a < b and b < c then a < c
 * antisymmetry: if a < b then not(b < a) , written also ~b<a
 * substitutability: if a == b and a < c then b < c
 * reflexivity: if a == a then not(a < a)
 * https://www.youtube.com/watch?v=Enwbh6wpnYs // see the double example :) NaN (whatever op) some value = false
 * https://www.youtube.com/watch?v=bvnmbRo7a1Y
 * "Consistent with equals", means that compare(a,b) == 0 means a.equals(b) == true, False!
 * It is only a nice property, not mandatory.
 * example: the scale notion in BigDecimal 1.0 vs 1.00 compare() == 0, but not equals.
 * example: TreeSet uses Comparator instead of equals() to determine the set membership
 */
public class ComparisonViolations {

	public static void main(String[] args) {

		violatedComparisonBigDecimal();

	}

	/**
	 * running this results in "Comparison method violates its general contract!"
	 * the stack trace leads to the comparator while the bug is in the code
	 * Seeds and ranges re carefully chosen
	 */
	private static void violatedComparison(){
		var random = new Random(209);
		var ints = IntStream.range(0, 32)
				.mapToObj(index -> random.nextInt())
				.toList();

		List<Integer> integers = ints.stream()
				.sorted(loggingComparator((i1, i2) -> i1 - i2))
				.toList();
		// the forth printed result is wrong: 1373845359  -1125089413  =>  -1796032524
		// must be a positive number... very big numbers, hence integer overflow

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

	private static void violatedComparisonDouble(){
		ArrayList<Double> collect = new Random(284397476).ints(100L)
				.map(Math::abs) // System.out.println(Math.abs(Integer.MIN_VALUE)); // -2147483648
				.mapToDouble(i -> Math.sqrt((double) i))
				.boxed()
				.collect(Collectors.toCollection(ArrayList::new));

		collect.sort(loggingComparator((i1, i2) -> i1 < i2 ? -1 : i1 > i2? 1 : 0)); // NaN (whatever op) some value = false
		//40133.504955336255  NaN  =>  0 // WTF :)
		System.out.println(collect);
	}

	private static void violatedComparisonBigDecimal(){
		var a = new BigDecimal("1.000");
		var b = new BigDecimal("1.0000");
		System.out.println(a.compareTo(b)); // true, aka 0
		System.out.println(a.equals(b)); // false
		var seven = new BigDecimal(7);
		var a2 = a.divide(seven, HALF_UP); // 0.143
		var b2 = b.divide(seven, HALF_UP); // 0.1429
		System.out.println(a2.compareTo(b2)); // false, aka 1
		System.out.println(a2.equals(b2)); // false
		//-----------------------------------
		//-----------------------------------
		Set<BigDecimal> hashset = new HashSet<>();
		Set<BigDecimal> treeset = new TreeSet<>();
		hashset.add(a);
		treeset.add(b);

		System.out.println(treeset.equals(hashset)); // true
		System.out.println(hashset.equals(treeset)); // false
		// TreeSet uses Comparator instead of equals() to determine the set membership.
		// Compare uses `this` to check against the other part.
		// so the treeset uses the comparator for which bigD(1.0) is the same as bigD(1.00)
		// but hashset uses equals() for which bigD(1.0) is not the same as bigD(1.00)
	}

	private static <T> Comparator<T> loggingComparator(Comparator<T> comparator){
		return (a, b) -> {
			int result = comparator.compare(a, b);
			System.out.println(a + "  " + b + "  =>  " + result);
			return result;
		};
	}



}

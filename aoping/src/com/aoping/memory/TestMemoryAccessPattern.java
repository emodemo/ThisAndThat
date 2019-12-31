package com.aoping.memory;

import java.util.Arrays;


/**
 * based on Martin Thompson article
 * perf stat -e L1-dcache-loads,L1-dcache-load-misses java -Xmx4g TestMemoryAccessPattern $
 * perf stat -e dTLB-loads,dTLB-load-misses java -Xmx4g TestMemoryAccessPattern $
 * likwid-perfctr -C 2 -g L2CACHE java -Xmx4g TestMemoryAccessPattern $
 *
 */
public class TestMemoryAccessPattern {

	private static final int LONG = 8;
	private static final int KILO = 1024;
	// private static final int MEGA = KILO * KILO;
	private static final int GIGA = KILO * KILO * KILO;
	private static final long TWO_GIGA = 2L * GIGA;

	private static final int PAGE = 2 * KILO * KILO;
	private static final int PAGE_WORDS = PAGE / LONG;
	private static final int ARRAY = (int) (TWO_GIGA / LONG);

	private static final int PAGE_WORD_MASK = PAGE_WORDS - 1;
	private static final int ARRAY_MASK = ARRAY - 1;

	private static final int PRIME_INC = 514229;
	private static final long LARGE_PRIME_INC = 70368760954879L;
	private static final long[] memory = new long[ARRAY];

	static {
		Arrays.fill(memory, 777);
	}

	public static void main(String[] args) {

		final StrideType type;
		switch (Integer.parseInt(args[0])) {
		case 1:
			type = StrideType.LINEAR_WALK;
			break;
		case 2:
			type = StrideType.RANDOM_PAGE_WALK;
			break;
		case 3:
			type = StrideType.RANDOM_HEAP_WALK;
			break;
		default:
			throw new IllegalArgumentException("Unknown Stride Type");
		}
		for (int i = 0; i < 5; i++) {
			performTest(i, type);
		}
	}

	private static void performTest(int run, StrideType type) {

		long start = System.nanoTime();
		int pos = -1;
		long result = 0L;
		for (int pageOffset = 0; pageOffset < ARRAY; pageOffset += PAGE_WORDS) {
			for (int wordOffset = pageOffset, limit = pageOffset + PAGE_WORDS; wordOffset < limit; wordOffset++) {
				pos = type.next(pageOffset, wordOffset, pos);
				result += memory[pos];
			}
		}
		long duration = System.nanoTime() - start;
		double nsOp = duration / (double) ARRAY;
		if (208574349312L != result) {
			throw new IllegalStateException();
		}
		System.out.format("%d - %.2fns %s\n", Integer.valueOf(run), Double.valueOf(nsOp), type);
	}

	enum StrideType {

		LINEAR_WALK {
			@Override
			int next(int pageOffset, int wordOffset, int pos) {
				return (pos + 1) & ARRAY_MASK;
			}
		},
		RANDOM_PAGE_WALK {
			@Override
			int next(int pageOffset, int wordOffset, int pos) {
				return pageOffset + ((pos + PRIME_INC) & PAGE_WORD_MASK);
			}
		},
		RANDOM_HEAP_WALK {
			@Override
			int next(int pageOffset, int wordOffset, int pos) {
				return (int) (pos + LARGE_PRIME_INC) & ARRAY_MASK;
			}
		};

		abstract int next(int pageOffset, int wordOffset, int pos);
	}
}

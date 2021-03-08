package io.creek;

import static io.creek.QuickSort.quickSort;
import static io.creek.UtilCompare.compare;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QuickSortTest {

	@Test
	void sortIntegers() {
		Integer[] tested = new Integer[] {1,1,1,2,3,1,5,6,9,8,7,4,0};
		Integer[] expected = new Integer[] {0,1,1,1,1,2,3,4,5,6,7,8,9};
		quickSort(tested, (x, y) -> Integer.compare(x, y));
		assertTrue(compare(tested, expected));
	}

	@Test
	void sortIntegersSame() {
		Integer[] tested = new Integer[] {3,3,3,3,3,3,3,3,3,3};
		Integer[] expected = new Integer[] {3,3,3,3,3,3,3,3,3,3};
		quickSort(tested, (x, y) -> Integer.compare(x, y));
		assertTrue(compare(tested, expected));
	}
	
	@Test
	void sortStrings() {
		String[] tested = new String[] {"K","R","A","T","E","L","E","P","U","I","M","Q","C","X","O","S"};
		String[] expected = new String[] {"A","C","E","E","I","K","L","M","O","P","Q","R","S","T","U","X"};
		quickSort(tested, (x, y) -> x.compareTo(y));
		assertTrue(compare(tested, expected));
	}

}

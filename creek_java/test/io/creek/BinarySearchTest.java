package io.creek;

import static io.creek.BinarySearch.binarySearch;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BinarySearchTest {

	@Test
	void itemExists() {
		assertTrue(binarySearch(new Integer[] {1,2,3,4,5,6,7,8,9,0}, 1, (x, y) -> Integer.compare(x, y)));
	}
	
	@Test
	void itemDoesntExist() {
		assertFalse(binarySearch(new Integer[] {1,2,3,4,5,6,7,8,9,0}, 10, (x, y) -> Integer.compare(x, y)));
	}

}

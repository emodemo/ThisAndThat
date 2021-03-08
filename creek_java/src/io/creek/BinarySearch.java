package io.creek;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class BinarySearch {

	public static <T> boolean binarySearch(T[] array, T value, Comparator<T> comparator) {
		Collections.sort(Arrays.asList(array), comparator);
		int lo = 0, hi = array.length - 1;
		while(lo <= hi) {
			int mid = (int) lo + ((hi-lo)/2);
			T midValue = array[mid];
			if		(comparator.compare(midValue, value) < 0) lo = mid + 1;
			else if	(comparator.compare(midValue, value) > 0) hi = mid - 1;
			else return true;
		}
		return false;
	}
	
}

package io.creek;

import java.util.Comparator;

public class MergeSort {

	public static <T> void mergeSort(T[] array, Comparator<T> comparator) {
		sort(array, null, comparator, 0, array.length - 1);
	}

	private static <T> void sort(T[] array, T[] aux, Comparator<T> comparator, int lo, int hi) {
		if (lo >= hi) return;
		int mid = (int) lo + ((hi - lo) / 2);
		sort(array, aux, comparator, lo, mid);
		sort(array, aux, comparator, mid + 1, hi);
		merge(array, aux, comparator, lo, mid, hi);
	}

	private static <T> void merge(T[] array, T[] aux, Comparator<T> comparator, int lo, int mid, int hi) {
		aux = array.clone();
		int left = lo, right = mid + 1; // use two pointers
		for(int k = lo; k <= hi; k++) {
			if 		(left > mid) array[k] = aux[right++]; // if nothing on the left side, so use right side
			else if (right > hi) array[k] = aux[left++];  // nothing on the right side, so use left side
			else if (comparator.compare(aux[left], aux[right]) > 0)
								 array[k] = aux[right++]; // right one is smaller, so use it
			else				 array[k] = aux[left++];  // left one is smaller, so use it
		}
	}
}

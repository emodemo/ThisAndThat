package io.creek;

import java.util.Comparator;

public class QuickSort {

	public static <T> void quickSort(T[] array, Comparator<T> comparator) {
		sort(array, comparator, 0, array.length - 1);
	}

	private static <T> void sort(T[] array, Comparator<T> comparator, int lo, int hi) {
		if (lo >= hi) return;
		int partition = partition(array, comparator, lo, hi);
		sort(array, comparator, lo, partition - 1);
		sort(array, comparator, partition + 1, hi);
	}

	private static <T> int partition(T[] array, Comparator<T> comparator, int lo, int hi) {
		int pivot = lo, left = lo + 1, right = hi;
		while (true) {
			while (left < hi && comparator.compare(array[left], array[pivot]) <= 0) left ++;
			while (right > lo && comparator.compare(array[right], array[pivot]) >= 0) right --;
			if (left >= right) break;
			swap(array, left, right);
		}
		swap(array, lo, right);
		return right;
	}
	
	private static <T> void swap(T[] array, int i, int j) {
		T tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
}

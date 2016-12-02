package hackerrank.ctci;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * <a href=
 * "https://www.hackerrank.com/challenges/ctci-find-the-running-median">https://www.hackerrank.com/challenges/ctci-find-the-running-median</a>
 * </br>
 * <b>Sample Input:</b> </br>
 * 6 </br>
 * 12 </br>
 * 4 </br>
 * 5 </br>
 * 3 </br>
 * 8 </br>
 * 7 </br>
 * <b>Sample Output:</b> </br>
 * 12.0 </br>
 * 8.0 </br>
 * 5.0 </br>
 * 4.5 </br>
 * 5.0 </br>
 * 6.0 </br>
 * 
 * @author emo
 *
 */
public class HeapsFindTheRunningMedian {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] numbers = new int[n];
		for (int i = 0; i < n; i++) {
			numbers[i] = in.nextInt();
		}

		// when even number both queues should have equal number of items,
		// otherwise maxQueue should have 1 more
		PriorityQueue<Integer> minQueue = new PriorityQueue<>(Comparator.reverseOrder());
		PriorityQueue<Integer> maxQueue = new PriorityQueue<>();

		for (int i : numbers) {
			// add to min or max
			if (!minQueue.isEmpty() && i <= minQueue.peek()) {
				minQueue.offer(i);
			} else {
				maxQueue.offer(i);
			}

			// balance the heaps
			while (minQueue.size() > maxQueue.size()) {
				maxQueue.offer(minQueue.poll());
			}
			while (maxQueue.size() - minQueue.size() > 1) {
				minQueue.offer(maxQueue.poll());
			}

			double median;
			if (minQueue.size() == maxQueue.size()) {
				median = (minQueue.peek() + maxQueue.peek()) / 2.0;
			} else {
				median = maxQueue.peek();
			}

			System.out.println(median);
		}

		in.close();
	}
}

package datastructures.heap;

import java.io.*;
import java.util.*;

public class FindTheRunningMedian {

    /*
     * Complete the runningMedian function below.
     */
    static double[] runningMedian(int[] a) {
        PriorityQueue<Integer> minQueue = new PriorityQueue<>(Comparator.reverseOrder());
        PriorityQueue<Integer> maxQueue = new PriorityQueue<>();
        double[] result = new double[a.length];
        int index = 0;
        for (int i : a) {
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
            result[index] = median;
            index++;
        }
        return result;

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int aCount = Integer.parseInt(scanner.nextLine().trim());

        int[] a = new int[aCount];

        for (int aItr = 0; aItr < aCount; aItr++) {
            int aItem = Integer.parseInt(scanner.nextLine().trim());
            a[aItr] = aItem;
        }

        double[] result = runningMedian(a);

        for (int resultItr = 0; resultItr < result.length; resultItr++) {
            bufferedWriter.write(String.valueOf(result[resultItr]));

            if (resultItr != result.length - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedWriter.close();
    }
}

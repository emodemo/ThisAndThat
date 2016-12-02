package hackerrank.ctci;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * <a href=
 * "https://www.hackerrank.com/challenges/ctci-array-left-rotation">https://www.hackerrank.com/challenges/ctci-array-left-rotation</a>
 * </br>
 * <b>Sample Input:</b> </br>
 * 5 4 </br>
 * 1 2 3 4 5 </br>
 * <b>Sample Output:</b> </br>
 * 4 </br>
 * 
 * @author emo
 *
 */
public class ArraysLeftRotation {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int k = in.nextInt();
		int input[] = new int[n];
		for (int i = 0; i < n; i++) {
			input[i] = in.nextInt();
		}

		leftRotation(input, k);

		System.out
				.println(String.join(" ", Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.toList())));

		in.close();
	}

	private static void leftRotation(int[] input, int rotation) {
		int rotate = rotation;

		reverse(input, 0, rotate - 1);
		reverse(input, rotate, input.length - 1);
		reverse(input, 0, input.length - 1);
	}

	private static void reverse(int[] input, int start, int end) {

		while (start < end) {
			int temp = input[start];
			input[start] = input[end];
			input[end] = temp;
			start++;
			end--;
		}
	}
}

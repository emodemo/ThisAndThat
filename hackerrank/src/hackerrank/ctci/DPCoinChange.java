package hackerrank.ctci;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <a href=
 * "https://www.hackerrank.com/challenges/ctci-coin-change">https://www.hackerrank.com/challenges/ctci-coin-change</a>
 * </br>
 * <b>Sample Input:</b> </br>
 * 4 3 </br>
 * 1 2 3 </br>
 * <b>Sample Output:</b> </br>
 * 4 </br>
 * 
 * @author emo
 *
 */
public class DPCoinChange {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int n = sc.nextInt();
		int m = sc.nextInt();
		int[] coins = new int[m];
		for (int i = 0; i < coins.length; i++) {
			coins[i] = sc.nextInt();
		}

		long[][] memo = new long[n + 1][m];
		for (int i = 0; i < memo.length; i++) {
			Arrays.fill(memo[i], -1);
		}

		Arrays.sort(coins);
		long i = countChange(n, coins, coins.length - 1, memo);
		System.out.println(i);

		sc.close();
	}

	private static long countChange(int remains, int[] coins, int index, long[][] memo) {
		long result = 0;
		if (remains == 0) {
			result = 1;
		}

		if (remains < 0) {
			return 0;
		}
		if (memo[remains][index] >= 0) {
			return memo[remains][index];
		}

		else if (remains > 0) {
			for (int i = index; i > -1; i--) {
				result += countChange(remains - coins[i], coins, i, memo);
			}
		}

		memo[remains][index] = result;
		return result;
	}
}

package codefights.practice;

import java.util.ArrayList;
import java.util.List;

public class Backtracking {

	int[][] climbingStaircase(int n, int k) {
		List<List<Integer>> result = new ArrayList<>();
		List<Integer> steps = new ArrayList<>();
		rec(result, steps, k, n);
		// convert results to expected format
		int[][] array = new int[result.size()][];
		for (int i = 0; i < result.size(); i++) {
			List<Integer> list = result.get(i);
			int[] blank = new int[list.size()];
			for (int j = 0; j < list.size(); j++) {
				blank[j] = list.get(j);
			}
			array[i] = blank;

		}
		return array;
	}

	private void rec(List<List<Integer>> result, List<Integer> steps, int finish, int left) {
		if (left == 0) {
			List<Integer> tmp = new ArrayList<>();
			tmp.addAll(steps);
			result.add(tmp);
		} else {
			for (int i = 1; i <= finish; i++) {
				if (i <= left) {
					steps.add(i);
					rec(result, steps, finish, left - i);
					steps.remove(steps.size() - 1);
				}
			}
		}
	}

	int[][] nQueens(int n) {
		List<List<Integer>> result = new ArrayList<>();
		dfs(result, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), n);
		// format result
		int[][] array = new int[result.size()][];
		for (int i = 0; i < result.size(); i++) {
			List<Integer> list = result.get(i);
			int[] blank = new int[list.size()];
			for (int j = 0; j < list.size(); j++) {
				blank[j] = list.get(j) + 1;
			}
			array[i] = blank;

		}
		return array;
	}

	// see explanations for diagonals here
	// https://leetcode.com/problems/n-queens/discuss/19810/Fast-short-and-easy-to-understand-python-solution-11-lines-76ms
	// In this problem, whenever a location (x, y) is occupied, any other locations (row, col) where row + col == x + y or row - col == x - y would be invalid.
	// -1(45 degree) and 1(135 degree) are the possible slopes. in y=(slope)x+b put in your coordinates, and check on the y intercept
	private void dfs(List<List<Integer>> result, List<Integer> queens, List<Integer> d1, List<Integer> d2,
			int totalQueens) {
		int row = queens.size();
		if (row == totalQueens) {
			List<Integer> tmp = new ArrayList<>();
			tmp.addAll(queens);
			result.add(tmp);
			return;
		}
		for (int col = 0; col < totalQueens; col++) {
			if (queens.contains(col) || d1.contains(row - col) || d2.contains(row + col))
				continue;
			queens.add(col);
			d1.add(row - col);
			d2.add(row + col);
			dfs(result, queens, d1, d2, totalQueens);
			// cleanup the last condition if not appropriate
			queens.remove(queens.size() - 1);
			d1.remove(d1.size() - 1);
			d2.remove(d2.size() - 1);
		}

	}
	
}


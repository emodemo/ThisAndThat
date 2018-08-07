package codefights.challanges;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Challanges {

	
	int[] logParse(String[] logs) {
		if (logs.length == 0)
			return new int[] {};
		int[] result = new int[2];
		double tmpBest = Double.MAX_VALUE;
		for (String log : logs) {
			String[] split = log.split(",");
			Timestamp timestamp = Timestamp.valueOf(split[0].replace("T", " "));
			Long time1 = timestamp.getTime() / 1000;
			Timestamp timestamp2 = Timestamp
					.valueOf(split[1].replace("T", " "));
			Long time2 = timestamp2.getTime() / 1000;
			int users = Integer.parseInt(split[2]);
			int profit = Integer.parseInt(split[3]);
			double res = (double) profit / (users * (time2 - time1));
			if (res < tmpBest) {
				tmpBest = res;
				result[0] = time1.intValue();
				result[1] = time2.intValue();
			}
		}
		return result;
	}

	int rightmostRoundNumber(int[] inputArray) {
		int x = -1, i = 0;
		for (int j : inputArray) {
			if (j % 10 == 0) x = i;
			i++;
		}
		return x;
	}

	// use a null hook to check for end of level in bfs
	// two hooks means end of all bfs
	int[][] christmasToys(int[][] toys) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(0); // the top
		queue.add(null); // the hook
		int level = 1;

		Map<Integer, List<Integer>> levels = new HashMap<>();
		levels.put(0, Collections.singletonList(0));
		levels.put(1, new ArrayList<>());
		while (!queue.isEmpty()) {
			Integer poll = queue.poll();
			// if hook is hit => end of level
			if (poll == null) {
				// two times hook means end
				if (queue.peek() == null) { 
					break;
				}
				level++; // level up
				queue.add(null); // add hook
				levels.put(level, new ArrayList<>());
				continue;
			}
			int[] children = toys[poll];
			for (int t : children) {
				queue.add(t);
				levels.get(level).add(t);
			}
		}
		// prepare the result in the required form
		int[][] result = new int[levels.size() - 1][];
		int index = 0;
		for (List<Integer> l : levels.values()) {
			if (l.size() == 0) {
				continue;
			}
			int[] x = new int[l.size()];
			for (int i = 0; i < l.size(); i++) {
				x[i] = l.get(i);
			}
			result[index] = x;
			index++;
		}
		return result;
	}

	// 18 of 22 , rest is Limit Exception
	long tableOfRemainders(int[] numbers) {
		int length = numbers.length;
		Arrays.sort(numbers);
		// hold already calculated rows
		Map<Integer, Long> map = new HashMap<>();
		long result = 0;
		for (int i = 0; i < length; i++) {
			Long res = map.get(Integer.valueOf(numbers[i]));
			if (res != null)
				result += res;
			else {
				long res2 = 0;
				// dividers grater then lead to * by the same number
				int index = greaterThanIndex(numbers, i, numbers[i]);
				if (index < length) res2 = (length - index) * numbers[i];
				for (int j = 0; j < index; j++) {
					if (numbers[i] > numbers[j]) res2 += (numbers[i] % (numbers[j]));
					else break;
				}
				map.put(Integer.valueOf(numbers[i]), res2);
				result += res2;
			}
		}
		return result;
	}

	int greaterThanIndex(int[] a, int l, int n) {
		int m = 0;
		int r = a.length - 1;
		while (l <= r) {
			m = l + (r - l) / 2;
			if (a[m] <= n)	l = m + 1;
			else r = m - 1;
		}
		return l;
	}

}

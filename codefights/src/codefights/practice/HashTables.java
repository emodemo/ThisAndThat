package codefights.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class HashTables {

	
	String swapLexOrder(String str, int[][] pairs) {
		// TODO:
		return null;
	}
	
	
	String[][] groupingDishes(String[][] dishes) {

		Map<String, List<String>> ingridients = new TreeMap<>();
		for (int i = 0; i < dishes.length; i++) {
			for (int j = 1; j < dishes[i].length; j++) {
				List<String> list = ingridients.get(dishes[i][j]);
				if (list == null) {
					list = new ArrayList<>();
					ingridients.put(dishes[i][j], list);
				}
				list.add(dishes[i][0]);
			}
		}

		String[][] result = new String[ingridients.size()][];
		int index = 0;
		for (Map.Entry<String, List<String>> entry : ingridients.entrySet()) {
			String k = entry.getKey();
			List<String> value = entry.getValue();
			if (value.size() < 2) continue;
			Collections.sort(value);
			String[] s = new String[value.size() + 1];
			s[0] = k;
			for (int i = 0; i < value.size(); i++) {
				s[i + 1] = value.get(i);
			}
			result[index] = s;
			index++;
		}

		String[][] result2 = new String[index][];
		for (int i = 0; i < index; i++) {
			result2[i] = result[i];
		}

		return result2;
	}

	// I still do not understand the question.. although the solution is correct
	// :-/
	boolean areFollowingPatterns(String[] strings, String[] patterns) {

		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < strings.length; i++) {
			if (map.containsKey(patterns[i])) {
				if (!map.get(patterns[i]).equals(strings[i])) return false;
			} else if (map.containsValue(strings[i])) return false;
			map.put(patterns[i], strings[i]);
		}
		return true;
	}

	// key is number, value is index
	// if there is index check absolute difference
	boolean containsCloseNums(int[] nums, int k) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			if (map.containsKey(nums[i])) {
				if ((i - map.get(nums[i])) <= k)
					return true;
				else
					map.put(nums[i], i);
			} else
				map.put(nums[i], i);
		}
		return false;
	}

	// keep cache of calculated sums, and build on them.
	int possibleSums(int[] coins, int[] quantity) {
		Set<Integer> sums = new HashSet<Integer>();
        sums.add(0); // starting sum, remove at the end
        for (int i = 0; i < quantity.length; ++i) {
           List<Integer> tmp = new ArrayList<Integer>(sums);
           for (Integer s : tmp) {
              for (int j = 1; j <= quantity[i]; ++j) {
                 sums.add(s + j * coins[i]);
              }
           }
        }
        return sums.size() - 1;
	}

}

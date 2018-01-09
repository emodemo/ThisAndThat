package codefights.challanges;

import java.sql.Timestamp;
import java.util.ArrayList;
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
			if (j % 10 == 0)
				x = i;
			i++;
		}
		return x;
	}

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
				if (queue.peek() == null) { // two times hook means end
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
		for(List<Integer> l : levels.values()){
			if(l.size() == 0){
				continue;
			}
			int[] x = new int[l.size()];
			for(int i = 0; i < l.size(); i++){
				x[i] = l.get(i);
			}
			result[index] = x;
			index ++;
		}
		
		return result;
	}

}

package codefights.arcade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SmoothSailing {

	
	String[] allLongestStrings(String[] inputArray) {
		Map<Integer, List<String>> map = new TreeMap<>();
		int max = 0;
		for(String s : inputArray) {
			int length = s.length();
			if(max < length) max = length;
			List<String> list = map.get(length);
			if(list == null) {
				list = new ArrayList<>();
				map.put(length, list);
			}
			list.add(s);
		}
		List<String> list = map.get(max);
		return list.toArray(new String[list.size()]);
	}
	
	
}

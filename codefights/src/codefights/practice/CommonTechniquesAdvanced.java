package codefights.practice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonTechniquesAdvanced {

	// rolling window, but with a rolling hash could be faster
	String[] repeatedDNASequences(String s) {
		if(s.length() < 10) return new String[] {};

		List<String> result = new ArrayList<>();
		Set<String> set = new HashSet<>();
		for(int i = 0; i < s.length() - 9; i++) {
			String sequence = s.substring(i, i+10);
			if(set.contains(sequence) && result.contains(sequence) == false) {
				result.add(sequence);
			} else {
				set.add(sequence);
			}
		}
		result.sort(Comparator.naturalOrder());
		return result.toArray(new String[] {});
	}
}

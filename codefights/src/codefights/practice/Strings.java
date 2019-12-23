package codefights.practice;

public class Strings {

	String amendTheSentence(String s) {
		// smarter solution, but not mine
		// String[] split = s.split("(?=[A-Z])");
		// return String.join(" ", split).toLowerCase();
		StringBuilder builder = new StringBuilder();
		boolean start = true;
		for (char c : s.toCharArray()) {
			if (Character.isUpperCase(c)) {
				if (start)
					builder.append(c);
				else
					builder.append(" ").append(c);
			} else
				builder.append(c);
			start = false;
		}
		return builder.toString().toLowerCase();
	}

	// KMP Algorithm for Pattern Searching
		int strstr(String s, String x) {
			if (s == null || x == null || s.length() < x.length())
				return -1;
			else if (x.isEmpty())
				return 0;

			int[] lps = lps(x);
			int i = 0;
			int j = 0;

			while (i < s.length()) {
				if (x.charAt(j) == s.charAt(i)) {
					i++;
					j++;
					if (j == x.length()) {
						return i - j; // match found. Return location of match
					}
				} else {
					if (j == 0) {
						i++;
					} else {
						j = lps[j - 1]; // backtrack j to check previous matching prefix
					}
				}
			}

			return -1;
		}

	
	private int[] lps(String str) {
		int[] lps = new int[str.length()];
		lps[0] = 0;
		int i = 1;
		int j = 0;

		while (i < str.length()) {
			if (str.charAt(i) == str.charAt(j)) {
				j++;
				lps[i] = j;
				i++;
			} else { // mismatch
				if (j == 0) { // go to next char
					lps[i] = 0;
					i++;
				} else { // backtrack j to check previous matching prefix
					j = lps[j - 1];
				}
			}
		}
		return lps;
	}
		
}

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
				if (start) builder.append(c);
				else builder.append(" ").append(c);
			} else	builder.append(c);
			start = false;
		}
		return builder.toString().toLowerCase();
	}

	// LTE exception on the last test, needs a trie
	int findFirstSubstringOccurrence(String s, String x) {
		char[] source = s.toCharArray();
		char[] target = x.toCharArray();

		int xl = x.length(), sl = s.length();
		int fi = -1, xi = 0, si = 0;

		while (true) {
			// find matching start char
			while (source[si] != target[xi]) {
				si++;
				if (si == sl) return -1;
			}
			// check the rest of the target
			fi = si;
			while (source[si] == target[xi]) {
				xi++;
				si++;
				if (xi == xl) return fi;
				if (si == sl) return -1;
			}
			// if not found => reset the target and continue
			fi = -1;
			xi = 0;
		}
	}

}

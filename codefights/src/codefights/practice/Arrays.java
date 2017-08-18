package codefights.practice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Arrays {

	int firstDuplicate(int[] a) {
		for (int i = 0; i < a.length; i++) {
			int current = a[i];
			if (current < 0)
				current = current *= -1;
			if (a[current - 1] < 0)
				return current;
			else
				a[current - 1] = a[current - 1] *= -1;
		}
		return -1;
	}

	char firstNotRepeatingCharacter(String s) {
		Set<Character> duplicates = new HashSet<>();
		List<Character> unique = new ArrayList<>();
		for (char c : s.toCharArray()) {
			Character ch = new Character(c);
			if (duplicates.contains(ch))
				continue;
			if (unique.contains(ch)) {
				unique.remove(ch);
				duplicates.add(ch);
			} else {
				unique.add(ch);
			}
		}
		if (unique.size() > 0)
			return unique.get(0);
		return '_';
	}

	int[][] rotateImage(int[][] a) {
		int l = a.length;
		for (int i = 0; i < l / 2; i++) {
			for (int j = i; j < l - i - 1; j++) {
				int tmp = a[i][j];
				a[i][j] = a[l - j - 1][i];
				a[l - j - 1][i] = a[l - i - 1][l - j - 1];
				a[l - i - 1][l - j - 1] = a[j][l - i - 1];
				a[j][l - i - 1] = tmp;
			}
		}
		return a;
	}

	static boolean isCryptSolution(String[] crypt, char[][] solution) {

		Map<Character, Character> sol = new HashMap<>();
		for (int i = 0; i < solution.length; i++) {
			sol.put(solution[i][0], solution[i][1]);
		}

		char[] a = crypt[0].toCharArray();
		String a1 = "";
		for (int i = 0; i < a.length; i++) {
			char tmp = sol.get(a[i]);
			if (i == 0 && tmp == '0' && a.length > 1)
				return false;
			a1 = a1 + tmp;
		}

		char[] b = crypt[1].toCharArray();
		String b1 = "";
		for (int i = 0; i < b.length; i++) {
			char tmp = sol.get(b[i]);
			if (i == 0 && tmp == '0' && b.length > 1)
				return false;
			b1 = b1 + tmp;
		}

		char[] c = crypt[2].toCharArray();
		String c1 = "";
		for (int i = 0; i < c.length; i++) {
			char tmp = sol.get(c[i]);
			if (i == 0 && tmp == '0' && c.length > 1)
				return false;
			c1 = c1 + tmp;
		}

		return Double.parseDouble(a1) + Double.parseDouble(b1) == Double.parseDouble(c1);
	}

	boolean isCryptSolution2(String[] crypt, char[][] solution) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < solution.length; j++)
				crypt[i] = crypt[i].replace(solution[j][0] + "", solution[j][1] + "");
			if (crypt[i].charAt(0) == '0' && crypt[i].length() > 1)
				return false;
		}
		return Double.parseDouble(crypt[0]) + Double.parseDouble(crypt[1]) == Double.parseDouble(crypt[2]);
	}

}

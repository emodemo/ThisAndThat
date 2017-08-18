package codefights.practice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class CommonTechniques {

	boolean containsDuplicates(int[] a) {
		Set<Integer> dup = new HashSet<Integer>();
		for (int i : a) {
			if (dup.contains(i))
				return true;
			dup.add(i);
		}
		return false;
	}

	boolean sumOfTwo(int[] a, int[] b, int v) {
		// java.util.Arrays.sort(a);
		java.util.Arrays.sort(b);
		for (int i = 0; i < a.length; i++) {
			// if(a[i] > v) break;
			int expected = v - a[i];
			int mid = 0, start = 0, end = b.length - 1;
			while (start <= end) {
				mid = (start + end) / 2;
				if (expected == b[mid])
					return true;
				if (b[mid] < expected)
					start = mid + 1;
				else
					end = mid - 1;
			}
		}
		return false;
	}

	int[] traverseTree(Tree<Integer> t) {
		if(t == null) return new int[]{};
		List<Integer> result = new ArrayList<>();
		Queue<Tree<Integer>> q = new LinkedList<>();
		q.add(t);
		while (!q.isEmpty()) {
			Tree<Integer> tmp = q.poll();
			result.add(tmp.value);
			if(tmp.left != null) q.add(tmp.left);
			if(tmp.right != null) q.add(tmp.right);
		}
		int[] i = new int[result.size()];
		int index = 0;
		for(Integer j : result){
			i[index] = j;
			index++;
		}
		return i;
	}

	private class Tree<T> {
		T value;
		Tree<T> left;
		Tree<T> right;
	}
}

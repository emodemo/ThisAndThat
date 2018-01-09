package codefights.practice;

import java.util.ArrayList;
import java.util.List;

public class Trees {

	class Tree<T> {
		T value;
		Tree<T> left;
		Tree<T> right;
	}

	boolean hasPathWithGivenSum(Tree<Integer> t, int s) {
		if (t == null && s == 0)
			return true;
		if (t == null)
			return false;
		List<Integer> maxes = new ArrayList<>();
		dfs(t, maxes, 0);
		return maxes.contains(s);
	}

	private void dfs(Tree<Integer> node, List<Integer> scale, Integer current) {
		if (node == null)
			return;
		if (node.left == null && node.right == null)
			scale.add(current + node.value);
		else {
			dfs(node.left, scale, current + node.value);
			dfs(node.right, scale, current + node.value);
		}
	}
	
	boolean isTreeSymmetric(Tree<Integer> t) {
		if(t == null) return true;
		return isMirror(t.left, t.right);
	}

	private boolean isMirror(Tree<Integer> t1, Tree<Integer> t2){
		if(t1 == null && t2 == null) return true;
		if(t1 != null && t2 != null && t1.value.equals(t2.value))
			return isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
		return false;
	}
	
	// go backwards 'till 1st level and calculate
	String findProfession(int level, int pos) {
		if(level == 1) return "Engineer";
		if(findProfession((level - 1), (pos+1)/2).equals("Engineer")){
			return (pos%2 ==1) ? "Engineer" : "Doctor";
		}
		return (pos%2 ==1) ? "Doctor" : "Engineer";
	}

}

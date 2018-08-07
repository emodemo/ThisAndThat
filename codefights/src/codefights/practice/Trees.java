package codefights.practice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Trees {

	class Tree<T> {
		Tree(T x) { value = x; }
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

	// for smallest in-order, for largest reverse in-order
	int kthSmallestInBST(Tree<Integer> t, int k) {
		Tree<Integer> node = inOrder(t, k, new int[] {0});
		return node.value;
	}
	
	private Tree<Integer> inOrder(Tree<Integer> node, int k, int[] count) {
		if(node == null) return node;
		Tree<Integer> left = inOrder(node.left, k, count);
		if(left != null) return left;
		count[0] ++;
		if(count[0] == k) return node;
		return inOrder(node.right, k, count);
	}
	
	// visit pre-order and check if trees are identical
	boolean isSubtree(Tree<Integer> t1, Tree<Integer> t2) {
		if(t2 == null) return true;
	    if(t1 == null) return false;
		// check (the pre-order part)
		if(areIdentical(t1, t2)) return true;
		// order
		if(isSubtree(t1.left, t2)) return true;
		if(isSubtree(t1.right, t2)) return true;
		return false;
	}
	
	private boolean areIdentical(Tree<Integer> root1, Tree<Integer> root2){
		if(root1 == null && root2 == null) return true;
		if(root1 == null || root2 == null) return false;
		if(!root1.value.equals(root2.value)) return false;
		if(!areIdentical(root1.left, root2.left)) return false;
		if(!areIdentical(root1.right, root2.right)) return false;
		return true;
	}
	
	// inorder=D,B,A,E,C,F preorder=A,B,D,C,E,F
	// 1) root is preorder[0] => A
	// 2) inorder => (left(D,B), root(A), right(C,E,F))
	// 3) preorder => (root(A), left(D,B), right(C,E,F)) // left from 1 to length - inorder index of A (2) => D and B
	Tree<Integer> restoreBinaryTree(int[] inorder, int[] preorder) {
		int index = indexOf(inorder, preorder[0]);
		int[] leftIn = new int[index];
		System.arraycopy(inorder, 0, leftIn, 0, index);
		int[] rightIn = new int[inorder.length - index - 1];
		System.arraycopy(inorder, index + 1, rightIn, 0, inorder.length - index - 1);

		int[] leftPre = new int[index];
		System.arraycopy(preorder, 1, leftPre, 0, index);
		int[] rightPre = new int[preorder.length - index - 1];
		System.arraycopy(preorder, index + 1, rightPre, 0, preorder.length - index - 1);
		
		Tree<Integer> node = new Tree<>(preorder[0]);
		if(leftIn.length != 0) node.left = restoreBinaryTree(leftIn, leftPre);
		if(rightIn.length != 0) node.right = restoreBinaryTree(rightIn, rightPre);
		
		return node;
	}
	
	
	private int indexOf(int[] a, int value){
		for(int i = 0; i < a.length; i++){
			if(a[i] == value) return i;
		}
		return -1;
	}
	
	class MyComparator implements Comparator<String>{
		@Override
		public int compare(String arg0, String arg1) {
			int l1 = arg0.length(), l2 = arg1.length();
			if(l1 > l2) return 1;
			else if(l1 < l2) return -1;
			return 0;
		}
	}
	
	String[] findSubstrings(String[] words, String[] parts) {
		String[] result = new String[words.length];
	    java.util.Arrays.sort(parts, new MyComparator());
		for(int i = 0; i < words.length; i++){
			String word = words[i];
			int tmpSize = 0;
			int tmpIndex = Integer.MAX_VALUE;
			String tmpWord = word;
			for(String part : parts){
	            int partLength = part.length();
	            if(tmpSize > partLength) break;
	            if(! word.contains(part)) continue;
				if(tmpSize < partLength || tmpIndex > word.indexOf(part)){
					tmpIndex = word.indexOf(part);
					tmpSize = partLength;
					tmpWord = word.replaceFirst(part, "["+ part +"]");
				}
			}
			result[i] = tmpWord;
		}
		return result;
	}

	// root , left, right
	void preOrder(Tree<Integer> node){
		if(node == null) return;
		System.out.println(node.value);
		preOrder(node.left);
		preOrder(node.right);
	}
	
	// left, root, right
	void inOrder(Tree<Integer> node){
		if(node == null) return;
		inOrder(node.left);
		System.out.println(node.value);
		inOrder(node.right);
	}

	// left, right, root
	void postOrder(Tree<Integer> node){
		if(node == null) return;
		postOrder(node.left);
		postOrder(node.right);
		System.out.println(node.value);
	}
	
}

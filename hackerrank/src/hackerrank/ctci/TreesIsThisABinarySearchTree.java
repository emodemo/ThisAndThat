package hackerrank.ctci;

/**
 * <a href=
 * "https://www.hackerrank.com/challenges/ctci-is-binary-search-tree">https://www.hackerrank.com/challenges/ctci-is-binary-search-tree</a>
 * </br>
 * 
 * @author emo
 *
 */
public class TreesIsThisABinarySearchTree {

	/* Hidden stub code will pass a root argument to the function below. Complete the function to solve the challenge. Hint: you may want to write one or more helper functions.  

	The Node class is defined as follows:
	    class Node {
	        int data;
	        Node left;
	        Node right;
	     }
	*/
		
	// Use Node instead of TreeNode
	    boolean checkBST(TreeNode root) {
	        
	       return checkBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
	    }
	    
	// must have in mind the min and max constrainst of the parent node
	    boolean checkBST(TreeNode node, int min, int max){
	        if(node == null){
	            return true;
	        }
	        if(node.data < min || node.data > max){
	            return false;
	        }
	        if(!checkBST(node.left, min, node.data-1)){
	            return false;
	        }
	        if(!checkBST(node.right, node.data+1, max)){
	            return false;
	        }
	        return true;
	        
	    }
	
}

class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;
 }

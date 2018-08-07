package codefights.practice;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeapsStacksQueues {

	int kthLargestElement(int[] nums, int k) {
	    java.util.Arrays.sort(nums);
	    int index = nums.length - k;
	    return nums[index];
	}
	
	String simplifyPath(String path) {
	    if (path == null || path.isEmpty()) return "/";
	    StringTokenizer st = new StringTokenizer(path, "/");
	    Deque<String> queue = new ArrayDeque<>();
	    // set the result path
	    while (st.hasMoreTokens()){
	       String token = st.nextToken();
	        if (token.trim().equals("..")){
	            if(!queue.isEmpty()) queue.pop();
	        }
	        else if(token.trim().equals(".")) {//do nothing
	        }
	        else queue.push(token);
	    }
	    if(queue.isEmpty()) return "/";
	    StringBuilder builder = new StringBuilder();
	    while(!queue.isEmpty()){
	    	builder.append("/").append(queue.removeLast());
	    }
	    return builder.toString();
	}
	
	String decodeString(String s) {
		Stack<String> str = new Stack<>();
		Stack<Integer> num = new Stack<>();
		int idx = 0;
		String tmpWord = "";
		Pattern p = Pattern.compile("^[\\d]+");
		while(idx < s.length()){
			// get the digit
			if(Character.isDigit(s.charAt(idx))){
				String substring = s.substring(idx);
				Matcher m = p.matcher(substring);
				m.find();
				String res = m.group(0);
				num.push(Integer.parseInt(res));
				idx += res.length();
			}
			else{
			// open
				if(s.charAt(idx) == '['){
				str.push(tmpWord);
				tmpWord = "";
				} // add current state N times
				else if(s.charAt(idx) == ']'){
					StringBuilder sb = new StringBuilder(str.pop());
					int n = num.pop();
					for(int i = 0; i < n; i++){
						sb.append(tmpWord);
					}
					tmpWord = sb.toString();
				}
				else{
					tmpWord += s.charAt(idx);
				}
				idx++;
			}
		}
		return tmpWord;
	}
	
	// worst case n^2 solution.
	// may use stack and go backwards for N solution
	int[] nextLarger(int[] a) {
		int[] r = new int[a.length];
		int nl = -1;
		for(int i = 0; i < a.length; i++){
			nl = i + 1;
			while(nl < a.length && a[i] >= a[nl]) {
				nl++;
			}
			if(nl == a.length) r[i] = -1;
			else r[i] = a[nl];
		}
		return r;
	}
	
	int[] minimumOnStack(String[] operations) {
		List<Integer> l = new ArrayList<>();
		MyStack stack = new MyStack();
		for(String s : operations){
			if(s.startsWith("push")) stack.push(Integer.parseInt(s.substring(5)));
			else if(s.startsWith("pop")) stack.pop();
			else l.add(stack.min());
		}
		int[] r = new int[l.size()];
		for(int i = 0; i < l.size(); i++){
			r[i] = l.get(i);
		}
		return r;
	}
	
	/** */
	@SuppressWarnings("serial")
	private class MyStack extends Stack<Integer>{
		public int min(){
			int m = Integer.MAX_VALUE;
			for(Object o : elementData){
				if(o instanceof Integer){
					int oo = ((Integer)o).intValue();
					if(oo < m) m = oo;
				}
			}
			return m;
		}
	}
	
	int countClouds(char[][] skyMap) {
		if(skyMap.length == 0) return 0;
		boolean[][] visited = new boolean[skyMap.length][skyMap[0].length];
		int clouds = 0;
		for(int i = 0; i < skyMap.length; i ++){
			for(int j = 0; j < skyMap[0].length; j++){
				if(!visited[i][j] && skyMap[i][j] == '1'){
					visit(skyMap, visited, i, j);
					clouds ++;
				}
			}
		}
		return clouds;
		
	}

	private void visit(char[][] skyMap, boolean[][] visited, int i, int j) {
		visited[i][j] = true;
		// go left
		if(j-1 > -1 && !visited[i][j-1] && skyMap[i][j-1] == '1'){
			 visit(skyMap, visited, i, j-1);
		}
		// go right
		if(j+1 < skyMap[0].length && !visited[i][j+1] && skyMap[i][j+1] == '1'){
			visit(skyMap, visited, i, j+1);
		}
		// go up
		if(i-1 > -1 && !visited[i-1][j] && skyMap[i-1][j] == '1'){
			visit(skyMap, visited, i-1, j);
		}
		// go down
		if(i+1 < skyMap.length  && !visited[i+1][j] && skyMap[i+1][j] == '1'){
			visit(skyMap, visited, i+1, j);
		}
	}
	
	int[] nearestGreater(int[] a) {
		int[] b = new int[a.length];
		int max = Arrays.stream(a).max().getAsInt();
		for (int i = 0; i < a.length; i++) {
			int index = 1;
			if(a[i] == max) b[i] = -1;
			else {
				while(true) {
					// go left, as the leftmost is prefered over the right
					if(i - index >= 0 && a[i - index] > a[i]) {
						b[i] = i - index; break;
					}
					// go right
					if(i + index < a.length && a[i + index] > a[i]) {
						b[i] = i + index; break;
					}
					// no more left or right path to go
					if(i - index < 0 && i + index >= a.length) {
						break;
					}
					index++;
				}
			}
		}
		return b;
	}
	
}

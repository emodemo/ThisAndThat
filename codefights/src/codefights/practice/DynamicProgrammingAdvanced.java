package codefights.practice;

public class DynamicProgrammingAdvanced {

	int maximalSquare(char[][] matrix) {
		int rows = matrix.length;
		int cols = matrix.length > 0 ? matrix[0].length : 0;
		int[][] aux = new int[rows + 1][cols + 1];

		int maxlen = 0;
		for (int r = 1; r <= rows; r++) {
			for (int c = 1; c <= cols; c++) {
				if (matrix[r - 1][c - 1] == '1') {
					aux[r][c] = Math.min(Math.min(aux[r - 1][c - 1], aux[r][c - 1]), aux[r - 1][c]) + 1;
					maxlen = Math.max(maxlen, aux[r][c]);
				}
			}
		}
		return maxlen * maxlen;
	}

	// If a star is present in the pattern, it will be in the second position p[1]
	// Then, we may ignore this part of the pattern, or delete a matching character
	// in the text.
	// If we have a match on the remaining strings after any of these operations,
	// then the initial inputs matched.
	boolean regularExpressionMatching(String s, String p) {
		return match(s, p);
	}

	private boolean match(String s, String p) {
		if (p.isEmpty())
			return s.isEmpty();
		boolean match = (!s.isEmpty() && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.'));
		if (p.length() >= 2 && p.charAt(1) == '*') {
			return match(s, p.substring(2)) || (match && match(s.substring(1), p));
		} else {
			return match && match(s.substring(1), p.substring(1));
		}
	}

	int paintHouses(int[][] cost) {
		if (cost == null || cost.length == 0)
			return 0;

		int len = cost.length;

		for (int i = 1; i < len; i++) {
			cost[i][0] += Math.min(cost[i - 1][1], cost[i - 1][2]);
			cost[i][1] += Math.min(cost[i - 1][0], cost[i - 1][2]);
			cost[i][2] += Math.min(cost[i - 1][0], cost[i - 1][1]);
		}

		return Math.min(cost[len - 1][0], Math.min(cost[len - 1][1], cost[len - 1][2]));
	}

	// take number and go backwards to see max sequence
	// save result an go to next number
	// get the maximum, n^2 time and n space
	int longestIncreasingSubsequence(int[] sequence) {
		int result = 0;
		if (sequence == null || sequence.length == 0)
			return result;

		int dp[] = new int[sequence.length];
		dp[0] = 1; // starting point
		result = 1; // starting point

		for (int i = 1; i < sequence.length; i++) {
			int tmpmax = 0;
			for (int j = 0; j < i; j++) {
				if (sequence[i] > sequence[j])
					tmpmax = Math.max(tmpmax, dp[j]);
			}
			dp[i] = tmpmax + 1;
			result = Math.max(result, dp[i]);
		}

		return result;
	}
	
		
	long countSmallerToTheRight(int[] nums) {
		if(nums == null) return 0;
		if(nums.length < 2) return 0;
		int[] aux = new int[nums.length];
		long[] c = {0};
		sort(nums, aux, 0, nums.length-1, c);
		return c[0];
	}
	
	private void sort(int[] a, int[] b, int lo, int hi, long[] count) {
		if(hi<=lo) return;
		
		int mid = lo + ((hi-lo)/2);
		
		sort(a, b, lo, mid, count);
		sort(a, b, mid+1, hi, count);
		merge(a, b, lo, hi, mid, count);
	}
	
	private void merge(int[] a, int[] b, int lo, int hi, int mid, long[] count) {
		
		// copy
		for(int k = lo; k <= hi; k++) {
			b[k] = a[k];
		}
		
		long shiftCount = 0;
		int i = lo, j = mid+1;
		for(int k = lo; k <= hi; k++) {
			// left half is done, use the right one only
			if     (i > mid) 	 { a[k] = b[j++]; } 
			// right half is done, use the left one only, augment the counter with N of shifts 
			else if(j > hi)  	 { a[k] = b[i++]; count[0] += shiftCount; }
			// right is smaller use it, add one to shifting positions
			else if(b[i] > b[j]) { a[k] = b[j++]; shiftCount++; }
			// left digit is smaller use it, augment the counter with N of shifts 
			else 				 { a[k] = b[i++]; count[0] += shiftCount; }
		}
	}

	public static void main(String[] args) {
		char[][] field = {{'0','0','E','0'}, 
		        {'W','0','W','E'}, 
		        {'0','E','0','W'}, 
		        {'0','W','0','E'}};
		DynamicProgrammingAdvanced advanced = new DynamicProgrammingAdvanced();
		int bomber = advanced.bomber(field);
		System.out.println(bomber);
	}
	
	
	int bomber(char[][] field) {

	    if(field == null || field.length == 0) return 0;
	    int rows = field.length;
	    int cols = field[0].length;

	    int[][] killed = new int[rows][cols];
	    int[] maxKilled = {0};
	    
	    for(int r = 0; r < rows; r++){
	        for(int c = 0; c < cols; c++){
	            if(field[r][c] == 'E'){
	                // go up
	                for(int i=r-1; i>=0; i--){
	                    if(!update(field, killed, maxKilled, i, c)) break;
	                }
	                // go down
	                for(int i=r+1; i<rows; i++){
	                    if(!update(field, killed, maxKilled, i, c)) break;
	                }
	                // go left
	                for(int i=c-1; i>=0; i--){
	                    if(!update(field, killed, maxKilled, r, i)) break;
	                }
	                // go right
	                for(int i=c+1; i < cols; i++){
	                    if(!update(field, killed, maxKilled, r, i)) break;
	                }
	            }
	        }
	    }
	    return maxKilled[0];
	    
	}

	boolean update(char[][] field, int[][] killed, int[] maxKilled, int r, int c){
	    if(field[r][c] == 'W') return false;
	    if(field[r][c] == '0') {
	        killed[r][c] ++;
	        if(maxKilled[0] < killed[r][c]) maxKilled[0] = killed[r][c];
	    }
	    return true;
	}
	
	// same as countSmallerToTheRight, just return the modulo 1000000007 (10^9+7) as integer
	int countInversions(int[] a) {
		
		long countSmallerToTheRight = countSmallerToTheRight(a);
        int mod = 1000000007;
		return (int)(countSmallerToTheRight % mod + mod) % mod;
	}
}

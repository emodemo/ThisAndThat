package datastructures.arrays;

public class ArrayManipulation {

	public static void main(String[] args) {
		int[][] queries = 
				// a,b,k
				{{1,2,100},
				{2,5,100},
				{3,4,100}};
		arrayManipulation(5, queries);
	}

	// prefix sum solution: where the a = k, b+1 = -k
	// this will give the actual value of each index
	// 1 2 100 => 100  100  0   0   0  => 100  0  -100 0  0
	// 2 5 100 => 100  200 100 100 100 => 100 100 -100 0  0
	// 3 4 100 => 100  200 200 200 100 => 100 100  0   0  -100
	// can be further updates to check only update values
	
	static long arrayManipulation(int n, int[][] queries) {
        long[] array = new long[n];
        for(int[] q : queries) {
        	array[q[0]-1] += q[2];
        	if(q[1] < n) array[q[1]] -= q[2];
        }
        
        long max = 0;
        long tmp = 0;
        for(long l : array) {
        	tmp += l;
        	if(tmp > max) max = tmp;
        }
        return max;
    }
}

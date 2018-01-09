package codefights.practice;

import java.util.HashSet;
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
	
	int sumInRange(int[] nums, int[][] queries) {
		long[] sums = new long[nums.length + 1];
		sums[0] = 0;
		long sum = 0;
		for(int i = 1; i < nums.length + 1; i++){
			sums[i] = sums[i-1] + nums[i-1];
		}
		for(int i = 0; i < queries.length; i++){
			int b = queries[i][1] + 1;
			int a = queries[i][0];
			sum += sums[b] - sums[a]; 
		}
		return (int) Math.floorMod(sum,1000000007);
	}
	
	int arrayMaxConsecutiveSum2(int[] inputArray) {
		int sum = 0,
			minSum = 0,
			result = inputArray[0];
	
		for (int i = 0; i < inputArray.length; i++) {
			sum += inputArray[i];
			if (sum - minSum > result) {
				result =  sum - minSum;
			}
			if (sum < minSum) {
				minSum = sum;
			}
		}
		return result;
	}
	
}

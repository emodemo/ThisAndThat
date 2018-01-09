package codefights.arcade;

import java.util.Arrays;

public class EdgeOfOcean {

	// Given an array of integers, find the pair of adjacent elements that has
	// the largest product and return that product.
	int adjacentElementsProduct(int[] inputArray) {
		int res = Integer.MIN_VALUE;
		for (int i = 0; i < inputArray.length - 1; i++) {
			int tmp = inputArray[i] * inputArray[i + 1];
			if (tmp > res) {
				res = tmp;
			}
		}
		return res;
	}

	// Below we will define an n-interesting polygon. Your task is to find the
	// area of a polygon for a given n
	int shapeArea(int n) {
		return n * n + (n - 1) * (n - 1);
	}

	// For statues = [6, 2, 3, 8], the output should be makeArrayConsecutive2(statues) = 3.
	// Ratiorg needs statues of sizes 4, 5 and 7.
	int makeArrayConsecutive2(int[] statues) {
		Arrays.sort(statues);
		int result = 0;
		for (int i = statues.length - 1; i > 0; i--) {
			if (statues[i] - statues[i - 1] != 1) {
				result += statues[i] - statues[i - 1] - 1;
			}
		}
		return result;
	}
	
	// s is total mone, mins are the cost per minute, return minutes to call
	// For min1 = 3, min2_10 = 1, min11 = 2 and s = 20, the output should be phoneCall(min1, min2_10, min11, s) = 14.
	int phoneCall(int min1, int min2_10, int min11, int s) {
		if(s < min1) return 0;
		int rest = s - min1;
		if(rest < min2_10*9){
			return 1 + (rest / min2_10);
		}
		rest -= min2_10*9;
		return 1 + 9 + (rest / min11);  
	}
}

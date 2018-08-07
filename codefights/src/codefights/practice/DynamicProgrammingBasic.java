package codefights.practice;

import java.util.ArrayList;
import java.util.List;

public class DynamicProgrammingBasic {

	int climbingStairs(int n) {
	    if(n<3) return n;
	    return climbingStairs(n-1) + climbingStairs(n-2);
	}

	int houseRobber(int[] nums) {
		int l = nums.length;
		if(l == 0) return 0;
		if(l == 1) return nums[0];
	    // in fact 3 ints are needed, the array is used to skip updating the 3 ints, 
	    // but for large arrays might be impractical
	    int[] tmp = new int[l];
	    tmp[0] = nums[0];
	    tmp[1] = Math.max(nums[0], nums[1]);
	    for(int i = 2; i < l; i++){
	        tmp[i] = Math.max(nums[i] + tmp[i-2], tmp[i-1]);
	    }
	    return tmp[l-1];
	}
	
	String[] composeRanges(int[] nums) {
		if(nums.length==0) return new String[0];
		List<String> result = new ArrayList<>();
		String entry = Integer.toString(nums[0]);
		int counter = 1;
		for(int i = 1; i < nums.length; i++) {
			if(nums[i] - nums[i-1] == 1) {
				counter ++;
				continue;
			}
			if(entry.equals(Integer.toString(nums[i-1])) == false) {
				entry = entry + "->" + Integer.toString(nums[i-1]);
			}
			result.add(entry);
			entry = Integer.toString(nums[i]);
			counter = 1;
		}
		if(counter != 1) {
			entry = entry + "->" + Integer.toString(nums[nums.length - 1]);
		}
		if(result.contains(entry) == false) result.add(entry);
		return result.toArray(new String[] {});
	}
	
	int mapDecoding(String message) {
		int previous = 0, result = 1;
		for (int i = 0; i < message.length(); i++) {
	        int counter = 0;
			// 1 - find the actual digit, where 48 is 0 in the ascii table
	        int digit = message.charAt(i) - 48;
	        // 2 - find if it is part of bigger digit
	        int bigDigit = 0;
	        if(i > 0) bigDigit = (message.charAt(i - 1) - 48) * 10 + digit;
	        // 3 - update counter if current is acceptable
	        if(digit > 0) counter = result;
	        // 4 - update counter if bigDigit is acceptable
	        if(bigDigit <= 26 && bigDigit > 9) counter += previous;
	        // 5 - save current state for next loop
	        previous = result;
	        // 6 - update with modulo as required
	        result = counter % 1000000007;
	    }
	    return result;
	}
}

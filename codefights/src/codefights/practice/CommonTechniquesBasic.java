package codefights.practice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class CommonTechniquesBasic {

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
		java.util.Arrays.sort(b);
		for (int i = 0; i < a.length; i++) {
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
	
	// here o(n).
	// can be done with two pointers for o(n^2), where 
	// 1st pointer starts from 0idx and increase the sum until a match is found or "s" is passed upside, // 1st N
	// 2nd pointer starts from 0idx and decrease the sum until a match is found or "s" is passed downside // 2nd N
	int[] findLongestSubarrayBySum(int s, int[] arr) {
		HashMap<Integer, Integer> cumsums = new HashMap<>();
		int maxLength = 0, sum = 0, start = 0, end = 0;
		for(int i = 0; i < arr.length; i++) {
			sum += arr[i];
			if(sum == s) {
				// cover case when longest starts form beginning of array
				maxLength = i + 1; 
				start = 1; 
				end = i + 1;
			}
			// check if there is a possible start index
			if(cumsums.containsKey(sum - s)) {
				int possibleStart = cumsums.get(sum - s);
				// if new length is longer => prefer it
				if(maxLength < i - possibleStart) {
					maxLength = i - possibleStart;
					start = possibleStart + 2;
					end = i + 1;
				}
			}
			cumsums.put(sum, i);
		}
		return (maxLength == 0) ? new int[] {-1} : new int[] {start, end};
	}
	
	// explanations with {1,2,3,4}, 12
	int productExceptSelf_v1(int[] nums, int m) {
		long[] f = new long[nums.length];
		Arrays.fill(f, 1);
		int g = 0;
		for(int i = 0; i < nums.length; i++) {
			for(int j = 0; j < nums.length; j++) {
				if(i != j) {
					//f[i] *= nums[j];
					f[i] = (f[i] * nums[j]) % m; // for faster calculation
				}
			}
			// f = {0,0,8,6} = modulo for {24,12,8,6} 
			g += f[i];
		}
		return g % m;
	}

	// explanations with {1,2,3,4}, 12
	int productExceptSelf_v2(int[] nums, int m) {
		long[] cumsumTillNow = new long[nums.length];
		Arrays.fill(cumsumTillNow, 1);
		// cumsumTillNow without current element (as per task request)
		for(int i = 1; i < nums.length; i++) {
			cumsumTillNow[i] = (cumsumTillNow[i-1]*nums[i-1]) % m; // with modulo to be faster
		}
		// cumsumTillNow = {1, 1, 2, 6}
		int g = 0;
		int cumsumAfterNow = 1; // can be done without array
		for(int i = nums.length - 1; i >= 0; i--) {
			g += (cumsumTillNow[i] * cumsumAfterNow) % m;
			cumsumAfterNow = (cumsumAfterNow * nums[i]) % m;
		}
		// g = {0, 0, 8, 6} = modulo for {24, 12, 8, 6} = 50
		// cumsumAfterNow {24, 12, 4, 1} * cumsumTillNow = {1, 1, 2, 6} = f {24, 12, 8, 6} 
		// cumsumAfterNow = {0, 0, 4, 1} = modulo for {24, 12, 4, 1}
		// cumsumAfterNow {0, 0, 4, 1} * cumsumTillNow = {1, 1, 2, 6} = f {0, 0, 8, 6}
		return g % m;
	}
	
	// cumsum[n] = cumsum * current element + cumproduct 
	int productExceptSelf_v3(int[] nums, int m) { 
	   int cumsum = 0;
	   int cumproduct = 1;
	   for (int i = 0; i < nums.length; i++) {
	      cumsum = cumsum * nums[i] + cumproduct;
	      cumsum %= m;
	      cumproduct *= nums[i];
	      cumproduct %= m;
	   }
	   return cumsum;
	}
	
	
	String minSubstringWithAllChars(String s, String t) {
		if(t.length() == 0) return "";
		char[] allT = t.toCharArray();
		int startPointer = 0, endPoiter = 0, size = 0, minStart = 0, minEnd = 0;
		for(int i = 0; i < s.length(); i++) {
			endPoiter = i;
			String substring = s.substring(startPointer, endPoiter + 1);
			if(containsAllChars(substring, allT)) {
				if(size == 0 || size > substring.length()){
					size = substring.length();
					minStart = startPointer;
					minEnd = endPoiter;
				}
			}
			boolean foundShorter = size != 0;
			while(foundShorter) {
				startPointer++;
				substring = s.substring(startPointer, endPoiter + 1);
				if(containsAllChars(substring, allT)) {
					if(size > substring.length()){
						size = substring.length();
						minStart = startPointer;
						minEnd = endPoiter;
					}
				}
				else {
					foundShorter = false;
					startPointer --;
				}
			}
		}
		return s.substring(minStart, minEnd + 1);
	}
	
	private boolean containsAllChars(String s, char[] ch) {
		for(char c : ch) {
			if(s.indexOf(c) == -1) return false;
		}
		return true;
	}
	

}

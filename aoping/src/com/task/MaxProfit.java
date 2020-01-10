package com.task;

// A[i] => profits from city A for each day 9am to 5pm
// B[i] => profits from city B for each day 9am to 5pm
// C 	=> traveling costs between cities at 10pm to 11pm
// both A and B are of size N
public class MaxProfit {

	// can we have negative values?
	// both A and B are of size N
	public static int maxProfit(int[] A, int[] B, int C) {
		if(A == null || A.length == 0) return 0;
		// count the max profit if starting from A and if starting from B, return the higher one
		int maxProfitA = A[0];
		int maxProfitB = B[0];
		boolean AA = true;
		boolean BB = true;
		int days = A.length; // both A and B are of size N		
		// no place to switch on the last day, just add it to the calcuation
		for(int i = 1; i < days - 1; i++) {
			// update maxProfitA
			if(AA) { // current city is A
				if(A[i] < B[i+1] + C) {
					AA = false; // go to city B
					maxProfitA += B[i] + C;		
				}else {
					AA = true; // go to city A, if not there
					maxProfitA += A[i];
				}
			}else { // if in 
				
			}
			// update maxProfitB
			if(BB) {
				if(B[i] < A[i+1] + C) {
					BB = false; // go to city A
					maxProfitB += A[i] + C;
				}else {
					BB = true; // go to city B, if not yet thre
					maxProfitB += B[i];
				}
			}
		}
		
		
		return maxProfitA > maxProfitB ? maxProfitA : maxProfitB;
	}
	
}

// cost 3
// 5, 1, 1, 1
// 4, 4, 4, 4

// cost 3
// 5, 1, 5, 1
// 4, 4, 4, 4
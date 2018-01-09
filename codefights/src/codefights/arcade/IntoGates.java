package codefights.arcade;

public class IntoGates {

	
	// For n = 29, the output should be addTwoDigits(n) = 11.
	int addTwoDigits(int n) {
		return (n % 10) + n / 10;
	}

	// For n = 2, the output should be largestNumber(n) = 99.
	int largestNumber(int n) {
		String res = "";
		for (int i = 0; i < n; i++) {
			res = res + 9;
		}
		return Integer.valueOf(res);
	}
	
	int candies(int n, int m) {
	    return (m/n)*n;
	}
	
	int seatsInTheater(int nCols, int nRows, int col, int row) {
	    return (nRows - row) * (nCols - col + 1);
	}
	
	int maxMultiple(int divisor, int bound) {
	    return (bound/divisor)*divisor;
	}

	int circleOfNumbers(int n, int firstNumber) {
	    int half = (n/2);
	    return (firstNumber < half) ? firstNumber + half: firstNumber - half ;
	}

	int lateRide(int n) {
		 int mins = n / 60;
		 int secs = n - mins * 60;
		 String nums = "" + mins + secs;
		 int result = 0;
		 for(char c : nums.toCharArray()){
		  result += Character.getNumericValue(c);
		 }
		 
		 return result;
	}
	
}

package codefights.arcade;

public class TheJourneyBegins {

	int add(int param1, int param2) {
	    return param1 + param2;
	}
	
	int centuryFromYear(int year) {
	    if(year % 100 == 0) return year/100;
	    return (year/100) + 1;
	}

	boolean checkPalindrome(String inputString) {
	    return inputString.equals(new StringBuilder(inputString).reverse().toString()); 
	}

}

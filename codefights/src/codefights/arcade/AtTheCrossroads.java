package codefights.arcade;

public class AtTheCrossroads {

	
	boolean reachNextLevel(int experience, int threshold, int reward) {
		return experience + reward >= threshold;
	}
	
	int knapsackLight(int value1, int weight1, int value2, int weight2, int maxW) {
		if(weight1 + weight2 <= maxW) return value1 + value2;
		if(weight1 <= maxW || weight2 <= maxW){
			if(weight1 <= maxW && weight2 <= maxW){
				if(value1 > value2) return value1;
				else return value2;
			}
			else{
				if(weight1 <= maxW) return value1;
				if(weight2 <= maxW) return value2;
			}
		}    
		return 0;
	}
	
	int extraNumber(int a, int b, int c) {
		if(a == b) return c;
		if(a == c) return b;
		return a;
	}
	
	boolean isInfiniteProcess(int a, int b) {
		return b<a?true:(b-a)%2==0?false:true;
	}

	boolean arithmeticExpression(int a, int b, int c) {
		if(a + b == c) return true;
		if(a - b == c) return true;
		if(a * b == c) return true;
		if((double)a / (double)b == (double)c) return true;
		return false;
	}
	
	boolean tennisSet(int score1, int score2) {
		if(score1==6 && score2<5) return true;
		if(score2==6 && score1<5) return true;
		if(score1==7 && (score2==5 || score2==6)) return true;
		if(score2==7 && (score1==5 || score1==6)) return true;
		return false;
	}

	boolean willYou(boolean young, boolean beautiful, boolean loved) {
		if(young && beautiful && !loved) return true;
		if(loved && (!young || !beautiful)) return true;
		return false;
	}
	
	int[] metroCard(int lastNumberOfDays) {
		return lastNumberOfDays == 31 ? new int[]{28,30,31} : new int[]{31};
	}

}

package hackerrank.ctci;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <a href=
 * "https://www.hackerrank.com/challenges/ctci-recursive-staircase">https://www.hackerrank.com/challenges/ctci-recursive-staircase</a>
 * </br>
 * <b>Sample Input:</b> </br>
 * 3 </br>
 * 1 </br>
 * 3 </br>
 * 7 </br>
 * <b>Sample Output:</b> </br>
 * 1 </br>
 * 4 </br>
 * 44 </br>
 * 
 * @author emo
 *
 */
public class RecursionDavisStaircase {

	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        int[] steps = {1, 2, 3};
        int nQueries = in.nextInt();
        for(int i = 0; i < nQueries; i++){
            int stairs = in.nextInt();
            int[] memo = new int[stairs];
		    Arrays.fill(memo, -1);
		    int result = count(stairs, steps, memo);
		    System.out.println(result);
        }
        
        in.close();
    }
    
    private static int count(int remains, int[] steps, int memo[]) {
		int res = 0;
		if(memo[remains - 1] >= 0){
			return memo[remains - 1];
		}
		for(int i = steps.length - 1; i > -1; i--){
			if(steps[i] == remains){
				res = 1;
			}
			else if(steps[i] < remains){
				res += count(remains - steps[i], steps, memo);
			}
		}
		memo[remains - 1] = res;
		return res;
	}
	
}

package hackerrank.ctci;

import java.util.Scanner;
import java.util.Stack;

public class StacksBalancedBrackets {

	private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            String expression = scanner.nextLine();
            boolean balanced = isBalanced(expression);
            if(balanced) System.out.println("YES");
            else System.out.println("NO");
        }

        scanner.close();
    }
    
    private static boolean isBalanced(String expression) {
    	Stack<Character> stack = new Stack<>();
        for(char c : expression.toCharArray()){
            switch (c) {
            	case '{': stack.push('}'); break;
            	case '(': stack.push(')'); break;
            	case '[': stack.push(']'); break;
            	default:
            		if(stack.isEmpty() || c != stack.peek()) return false;
            		stack.pop();
            }
        }
        return stack.isEmpty();
    }
	
}

package datastructures.stack;

import java.io.*;
import java.util.*;

public class BalancedBrackets {

    // Complete the isBalanced function below.
    static String isBalanced(String s) {
        Stack<Character> stack = new Stack<>();
        for(char c : s.toCharArray()){
            switch (c) {
                case '{': stack.push('}'); break;
                case '(': stack.push(')'); break;
                case '[': stack.push(']'); break;
                default:
                    if(stack.isEmpty() || c != stack.peek()) return "NO";
                    stack.pop();
            }
        }
        return stack.isEmpty() ? "YES" : "NO";

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = scanner.nextInt();
        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

        for (int tItr = 0; tItr < t; tItr++) {
            String s = scanner.nextLine();

            String result = isBalanced(s);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }

        bufferedWriter.close();

        scanner.close();
    }
}

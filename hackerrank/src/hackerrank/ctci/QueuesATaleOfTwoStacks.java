package hackerrank.ctci;

import java.util.Scanner;
import java.util.Stack;

/**
 * <a href=
 * "https://www.hackerrank.com/challenges/ctci-queue-using-two-stacks">https://www.hackerrank.com/challenges/ctci-queue-using-two-stacks</a>
 * </br>
 * @author emo
 *
 */
public class QueuesATaleOfTwoStacks {
	public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<Integer>();

        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();

        for (int i = 0; i < n; i++) {
            int operation = scan.nextInt();
            if (operation == 1) { // enqueue
              queue.enqueue(scan.nextInt());
            } else if (operation == 2) { // dequeue
              queue.dequeue();
            } else if (operation == 3) { // print/peek
              System.out.println(queue.peek());
            }
        }
        scan.close();
    }
}

class MyQueue<T> {
	Stack<T> newStack = new Stack<T>();
	Stack<T> oldStack = new Stack<T>();

	void enqueue(T item) {
		newStack.push(item);
	}

	T dequeue() {
		shift();
		return oldStack.pop();
	}

	T peek() {
		shift();
		return oldStack.peek();
	}

	void shift() {
		if (oldStack.empty()) {
			while (!newStack.empty()) {
				oldStack.push(newStack.pop());
			}
		}
	}
}
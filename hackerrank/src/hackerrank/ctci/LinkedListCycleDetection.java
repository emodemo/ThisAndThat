package hackerrank.ctci;

/**
 * See <a href=
 * "https://www.hackerrank.com/challenges/ctci-linked-list-cycle">https://www.hackerrank.com/challenges/ctci-linked-list-cycle</a>
 * </br>
 * 
 * @author emo
 *
 */
public class LinkedListCycleDetection {

	/*
	 * Detect a cycle in a linked list. Note that the head pointer may be 'null'
	 * if the list is empty.
	 * 
	 * A Node is defined as: class Node { int data; Node next; }
	 */

	boolean hasCycle(Node head) {
		Node slow = head;
		Node fast = head;

		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
			if (slow == fast) {
				return true;
			}
		}
		return false;
	}

	private class Node {
		Node next;
	}

}

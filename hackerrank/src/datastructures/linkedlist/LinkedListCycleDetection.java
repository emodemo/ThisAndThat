package datastructures.linkedlist;

public class LinkedListCycleDetection {

	static boolean hasCycle(SinglyLinkedListNode head) {
		if(head == null || head.next == null) return false;
		SinglyLinkedListNode slow = head;
		SinglyLinkedListNode fast = head.next;
		while(fast != null && fast.next != null) {
			if(slow == fast) return true;
			slow = slow.next;
			fast = fast.next.next;
		}
		return false;
    }
}

class SinglyLinkedListNode {
	int data;
    SinglyLinkedListNode next;
}
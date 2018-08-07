package codefights.practice;

import java.util.Stack;

public class LinkedList {

	private class ListNode<T> {
		T value;

		ListNode(T t) {
			value = t;
		}

		ListNode<T> next;

		@Override
		public String toString() {
			return value.toString();
		}
	}

	ListNode<Integer> removeKFromList(ListNode<Integer> l, int k) {
		ListNode<Integer> first = null;
		ListNode<Integer> last = null;
		ListNode<Integer> tmp = l;
		while (tmp != null) {
			if (tmp.value != k) {
				if (first == null) {
					first = new ListNode<>(tmp.value);
				} else if (last == null) {
					last = new ListNode<>(tmp.value);
					first.next = last;
				} else {
					last.next = new ListNode<>(tmp.value);
					last = last.next;
				}
			}
			tmp = tmp.next;
		}
		return first;
	}

	boolean isListPalindrome(ListNode<Integer> l) {
		// better solution is go to the middle; reverse the second; compare both
		Stack<ListNode<Integer>> stack = new Stack<>();
		ListNode<Integer> tmpNode = l;
		while (tmpNode != null) {
			stack.add(tmpNode);
			tmpNode = tmpNode.next;
		}
		while (!stack.isEmpty()) {
			ListNode<Integer> pop = stack.pop();
			if (!l.value.equals(pop.value))
				return false;
			l = l.next;
		}
		return true;
	}

	ListNode<Integer> addTwoHugeNumbers(ListNode<Integer> a, ListNode<Integer> b) {
		ListNode<Integer> newA = reverse(a);
		ListNode<Integer> newB = reverse(b);
		ListNode<Integer> result = null;
		int value = 0;
		while (newA != null || newB != null) {
			if (newA != null) {
				value += newA.value;
				newA = newA.next;
			}
			if (newB != null) {
				value += newB.value;
				newB = newB.next;
			}
			if (result == null) {
				result = new ListNode<>(value % 10000);
			} else {
				ListNode<Integer> tmp = new ListNode<>(value % 10000);
				tmp.next = result;
				result = tmp;
			}
			value = value / 10000;
		}
		// if there is something left, add a node
		if (value > 0) {
			ListNode<Integer> tmp = new ListNode<>(value);
			tmp.next = result;
			result = tmp;
		}
		return result;
	}

	private ListNode<Integer> reverse(ListNode<Integer> node) {
		ListNode<Integer> current = node;
		ListNode<Integer> previous = null;
		while (current != null) {
			ListNode<Integer> next = current.next;
			current.next = previous;
			previous = current;
			current = next;
		}
		return previous;
	}

	ListNode<Integer> mergeTwoLinkedLists(ListNode<Integer> l1,
			ListNode<Integer> l2) {
		// keep reference to the start node
		ListNode<Integer> head = new ListNode<>(0);
		ListNode<Integer> tmp = head;
		while (l1 != null && l2 != null) {
			int value = 0;
			if (l1.value <= l2.value) {
				value = l1.value;
				l1 = l1.next;
			} else {
				value = l2.value;
				l2 = l2.next;
			}
			tmp.next = new ListNode<>(value);
			tmp = tmp.next;
		}
		if (l1 == null) {
			tmp.next = l2;
		}
		if (l2 == null) {
			tmp.next = l1;
		}
		// exclude the first artificially created node with value 0
		return head.next;
	}

	ListNode<Integer> reverseNodesInKGroups(ListNode<Integer> l, int k) {
		if(l==null || l.next==null || k==1) return l;
		ListNode<Integer> result = new ListNode<>(0);
		result.next = l;	
		ListNode<Integer> tmp = result;
		int i = 0;
		while(l != null){
			i++;
			if(i % k == 0){
				tmp = doReverse(tmp, l.next);
				l = tmp.next;
			}else{
				l = l.next;
			}
		}
		return result.next;
	}	


	private ListNode<Integer> doReverse(ListNode<Integer> start, ListNode<Integer> end){
		ListNode<Integer> previous = start;
		ListNode<Integer> current = previous.next;
		ListNode<Integer> next = current.next;
		while(next != end){
			current.next = next.next;
			next.next = previous.next;
			previous.next = next;
			next = current.next;
		}
		return current;
	}
	
	// could be done with fast/slow pointers but with additional space O(n)
	ListNode<Integer> rearrangeLastN(ListNode<Integer> l, int n) {
		if(l == null || l.next == null || n == 0) return l;
		int length = 1;
		ListNode<Integer> tmp = l;
		while(tmp.next != null){
			length++;
			tmp = tmp.next;
		}
		tmp.next = l;
		int rest = n % length;
		for(int i = 0; i < (length-rest); i++){
			 tmp= tmp.next;
		}
		l = tmp.next;
	    tmp.next = null;
	    return l;
	}
	
}

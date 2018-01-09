package codefights.practice;

import java.util.Stack;

public class LinkedList {

	private class ListNode<T>{
		T value;
		ListNode(T t) { value = t; }
		ListNode<T> next;
	}
	
	ListNode<Integer> removeKFromList(ListNode<Integer> l, int k){
		ListNode<Integer> first = null;
		ListNode<Integer> last = null;
		ListNode<Integer> tmp = l;
		while(tmp != null){
			if(tmp.value != k){
				if(first == null){
					first = new ListNode<>(tmp.value);
				}else if(last == null){
					last = new ListNode<>(tmp.value);
					first.next = last;
				}else{
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
		while(tmpNode != null){
			stack.add(tmpNode);
			tmpNode = tmpNode.next;
		}
		while(!stack.isEmpty()){
			ListNode<Integer> pop = stack.pop();
			if(!l.value.equals(pop.value)) return false;
			l = l.next;
		}
		return true;
	}
}

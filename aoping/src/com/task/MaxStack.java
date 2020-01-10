package com.task;

import java.util.LinkedList;
// TASK: implement methods with:
// popMax() needs to be of O(1) complexity, there are no other limitations.

// seems like it can be done with O(log n), with 3 lists (value, idx, removed from popMax()(, but push is still an issue.
public class MaxStack<T extends Comparable<T>> {

	private int maxIdx;
	LinkedList<Tuple<T>> list = new LinkedList<>();
	
	public T pop() {
		// use normal stack ordering (last in - first out)
		list.sort((o1, o2) -> Integer.compare(o2.idx, o1.idx)); // O(n log(n))
		T result = popMax(); // pop with the LIFO order
		// get back to order by Max Value
		list.sort((o1, o2) -> o2.value.compareTo(o1.value));  // O(n log(n))
		return result;
	}
	
	public T popMax() {
		// pop whatever the order is, O(1) operation.
		// however at this moment the order should be Max Value unless called from pop()
		Tuple<T> pop = list.pop();
		maxIdx = maxIdx == pop.idx ? --maxIdx : maxIdx; 
		return pop.value;
	}
	
	public void push(T t) {
		// push in the list, and idx for LIFO order when required
		list.push(new Tuple<T>(t, maxIdx++));
		// ensure Max Value order is used, in case next operation is popMax()
		list.sort((o1,o2) -> o2.value.compareTo(o1.value));  // O(n log(n))
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	static class Tuple<T extends Comparable<T>>{
		T value;
		int idx;
		
		public Tuple(T value, int idx) {
			this.value = value;
			this.idx = idx;
		}
	}
}

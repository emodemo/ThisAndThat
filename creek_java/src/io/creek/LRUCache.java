package io.creek;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, T> {
	// TODO: not tested
	private Node<K, T> head = new Node<K, T>(null, null), tail = new Node<K, T>(null, null);
	private Map<K, Node<K, T>> map = new HashMap<>();
	private int capacity;
	
	public LRUCache(int capacity) {
		super();
		this.capacity = capacity;
	}

	public T get(K key) {
		if(map.containsKey(key) == false) return null; // it is not in the cache
		Node<K, T> result = map.get(key);
		remove(result);
		insert(result);
		return result.value;
	}
	
	public void put(K key, T value) {
		if(map.containsKey(key)) remove(map.get(key));
		if(map.size() == capacity) remove(tail.prev);
		insert(new Node<K, T>(key, value));
	}
	
	private void remove(Node<K, T> node) {
		map.remove(node.key);
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}
	
	// put on top, or after the dummy head in this case
	private void insert(Node<K, T> node) {
		map.put(node.key, node);
		Node<K, T> headnext = head.next;
		head.next = node;
		node.prev = head;
		headnext.prev = node;
		node.next = headnext;
	}
	
	private static class Node<K, T> {
		private K key;
		private T value;
		private Node<K, T> prev, next;
		
		private Node(K key, T value) {
			super();
			this.key = key;
			this.value = value;
		}
	}
}

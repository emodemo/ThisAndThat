package com.aoping.sync;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Although counting Semaphore is also a good solution
// Another option is to replace Lock with synchronized keyword and use 2 objects instead of Conditions
// JAVA has BlockingQueue impl, that is not bad
// see JCTools and Disruptor too
public class BlockingQueueV1<E> {

	private Queue<E> queue;
	private Lock lock = new ReentrantLock(true);
	private Condition empty = lock.newCondition();
	private Condition full = lock.newCondition();
	private int maxSize = 5; // could be in constructor

	public BlockingQueueV1(int size){
		queue = new LinkedList<>();
		maxSize = size;
	}

	public void put(E e) throws InterruptedException {
		lock.lock();
		try {
			while (queue.size() >= maxSize) {
				full.await(); // cannot add anymore => wait
			}
			queue.add(e); // after waiting (e.g. notified) => do what is expected
			empty.signalAll(); // notify empty that it is not empty anymore			
		} finally {
			lock.unlock();
		}
	}

	public E take() throws InterruptedException {
		lock.lock();
		try {
			while (queue.size() == 0) {
				empty.await(); // wait for something to be put in the queue
			}
			E e = queue.poll(); // get what is put inside
			full.signalAll(); // notify full that is is not full anymore
			return e;
		} finally {
			lock.unlock();
		}
	}
}

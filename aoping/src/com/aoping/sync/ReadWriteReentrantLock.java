package com.aoping.sync;

import java.util.HashMap;
import java.util.Map;

public class ReadWriteReentrantLock {

	// TODO:
	// in lockWrite => if it is the only reader => give write access too
	// in lockRead => if it is a writer give write access. 
	
//	private int readers;
	private Map<Thread, Integer> readers = new HashMap<>();
	private int writerAccesses;
	private int writeRequests;
	private Thread writingThread = null;
	
	
	public synchronized void lockRead() throws InterruptedException {
		Thread callingThread = Thread.currentThread();
		while(writerAccesses > 0 || writeRequests > 0 || !readers.containsKey(callingThread)) {
			wait();
		}
		//readers++;
		Integer accessCount = readers.getOrDefault(callingThread, 0);
		readers.put(callingThread, accessCount + 1);
	}
	
	
	// notifyAll() on both unlocks
	// 1 - in case READ was awaken with notify, but there was WRITE waiting
	// however with notify they will not be awaken => both READ/WRITE will continue to wait 
	public synchronized void unlockRead() {
	//	readers--;
		Thread callingThread = Thread.currentThread();
		Integer accessCount = readers.getOrDefault(callingThread, 1);// should never be default value
		if(accessCount == 1) {
			readers.remove(callingThread);
		}
		else {
			readers.put(callingThread, accessCount - 1); 
		}
		notifyAll();
	}
	
	public synchronized void lockWrite() throws InterruptedException {
		Thread callingThread = Thread.currentThread();
		writeRequests++;
		while(readers.size() > 0 || writingThread != null || writingThread != callingThread) {
			wait();
		}
		writeRequests--;
		writerAccesses++;
		writingThread = callingThread;
	}
	
	public synchronized void unlockWrite() {
		writerAccesses--;
		if(writerAccesses == 0) {
			writingThread = null;
		}
		notifyAll();
	}
}

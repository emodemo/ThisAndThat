package com.aoping.sync;

public class ReadWriteLock {

	private int readers;
	private int writers;
	private int writeReqests;
	
	
	public synchronized void lockRead() throws InterruptedException {
		while(writers > 0 || writeReqests > 0) {
			wait();
		}
		readers++;
	}
	
	
	// notifyAll() on both unlocks
	// 1 - in case READ was awaken with notify, but there was WRITE waiting
	// however with notify they will not be awaken => both READ/WRITE will continue to wait 
	public synchronized void unlockRead() {
		readers--;
		notifyAll();
	}
	
	public synchronized void lockWrite() throws InterruptedException {
		writeReqests++;
		while(readers > 0 || readers > 0) {
			wait();
		}
		writeReqests--;
		writers++;
	}
	
	public synchronized void unlockWrite() {
		writers--;
		notifyAll();
	}
}

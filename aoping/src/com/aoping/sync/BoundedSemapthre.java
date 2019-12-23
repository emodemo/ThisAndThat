package com.aoping.sync;

// commented is the Lock alternative for a single signal
public class BoundedSemapthre {

	private int signals = 0;
	private int bound = 0;
	// private boolean locked = false;
	
	public BoundedSemapthre(int bound) {
		this.bound = bound;
	}
	
	public synchronized void take() throws InterruptedException {
		while(signals == bound) {
			wait(); // current thread wait until another one calls notofy for this object
		}
		signals ++;
		notify(); // wake up a thread waiting on this object's monitor
		
		//*********//
//		while(locked) wait();
//		locked = true;
		
	}
	
	public synchronized void release() throws InterruptedException {
		while (signals == 0) {
			wait();
		}
		signals --;
		notify();
		
		//**********//
//		locked = false;
//		notify();
	}
	
}

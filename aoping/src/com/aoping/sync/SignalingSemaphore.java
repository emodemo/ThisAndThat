package com.aoping.sync;


// to ensure wait and notify are in the right order and a signal is not missed
// even if take() happens before release(), the thread calling release will know that take() was called

// A thread that calls a wait() on any object, become inactive until someone calls notify() on that same object.
// in order to call wait/notify a thread must own the lock on that object otherwise IllegalMonitorStateException
// use a while(condition) instead of if(condition) to guard against spurious wake-ups 
//(when a thread is waked-up without calling notify())
public class SignalingSemaphore {

	public static void main(String[] args) {
		SignalingSemaphore semaphore = new SignalingSemaphore();
		Thread t1 = new SendingThread(semaphore); 
		Thread t2 = new ReceivingThread(semaphore);
		t1.start();
		t2.start();
	}
	
	private boolean signal = false;

	//doNotify()
	public synchronized void take() {
		signal = true;
		notify(); // wake up a thread waiting on this object's monitor
	}
	// doWait
	public synchronized void release() throws InterruptedException {
		while(signal == false) wait(); // current thread wait until another one calls notify for this object
		signal = false;
	}
	
}

class SendingThread extends Thread{
	SignalingSemaphore semaphore;

	public SendingThread(SignalingSemaphore semaphore) {
		super();
		this.semaphore = semaphore;
	}
	@Override
	public void run() {
		while (true) {
			// do something than notify
			semaphore.take();
		}
	}
}
class ReceivingThread extends Thread{
	SignalingSemaphore semaphore;

	public ReceivingThread(SignalingSemaphore semaphore) {
		super();
		this.semaphore = semaphore;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				semaphore.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// receive signal and do something
		}
	}
	
}
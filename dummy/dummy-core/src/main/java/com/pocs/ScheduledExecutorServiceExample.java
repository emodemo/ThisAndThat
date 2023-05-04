package com.pocs;

import java.time.Instant;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {

	private final static ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10);

	public static void main(String[] args) {

		scheduledExecutorService.scheduleAtFixedRate(() -> scheduledExecutorService.submit(
				logOnException(ScheduledExecutorServiceExample::doSomething)),
				0,
				1,
				TimeUnit.SECONDS
		);
	}

	private static void doSomething(){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " " + Instant.now().toString());
	}

	public static Runnable logOnException(Runnable runnable) {
		return () -> {
			try {
				runnable.run();
			} catch (Exception ex) {
				System.out.println("Something went wrong");
			}
		};
	}

}

package com.example.services;

import lombok.RequiredArgsConstructor;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
//import javax.ejb.Asynchronous;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.Future;

@Stateless
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class SyncAsyncService {

    // @PersistenceContext(name = "punit")
	// using constructor injection instead
	private final EntityManager entityManager;

	public SyncAsyncService() {
		this(null);
	}

//	@Inject
//	public SyncAsyncService(EntityManager entityManager) {
//		this.entityManager = entityManager;
//	}

	public void printBefore(String message){
		System.out.println("before: " + message + " - " + this + "   " + System.identityHashCode(this));
	}

	public void printAfter(String message){
		System.out.println("after: " + message + " - " + this + "   " + System.identityHashCode(this));
	}

//	@Asynchronous
	public void printAsync(String message){
	//	System.out.println("after: " + message + " - " + System.identityHashCode(this));
		try {
			System.out.println(" ================= START SYNC 2 ============ " + this + "   " + System.identityHashCode(this));
			Thread.sleep(10000);
			System.out.println(" ================= STOP SYNC2 ============ " + this + "   " + System.identityHashCode(this));
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}

//	@Asynchronous
	public Future<Boolean> printAsync2(String message){
		System.out.println("after: " + message + " - " + this + "   " + System.identityHashCode(this));
		return new AsyncResult<>(true);
	}


	//@Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
	public void scheduled(){
		try {
			System.out.println(" ================= START SCHEDULED ============ " + this + "   " + System.identityHashCode(this));
			Thread.sleep(10000);
			System.out.println(" ================= STOP SCHEDULED ============ " + this + "   " + System.identityHashCode(this));
		} catch (InterruptedException e) {
			System.out.println(e.toString());
		}
	}

}

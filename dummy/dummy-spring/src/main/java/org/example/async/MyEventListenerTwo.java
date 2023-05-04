package org.example.async;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MyEventListenerTwo {

	@EventListener
	@Async
	@Order
	public void listenTo(MyEvent event) throws InterruptedException {
		System.out.println("two start" + event );
		Thread.sleep(2000);
		System.out.println("two end" + event );
	}

}

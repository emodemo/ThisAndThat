package org.example.async;

import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MyEventListenerOne {

	@EventListener
	@Async
	public void listenTo(MyEvent event) throws InterruptedException {
		System.out.println("one start" + event );
		Thread.sleep(2000);
		System.out.println("one end" + event );
	}

}

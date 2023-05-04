package org.example.async;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("event")
@RequiredArgsConstructor
public class MyEventController {

	private final ApplicationEventPublisher publisher;

	@GetMapping()
	public void setEvent(){
		publisher.publishEvent(new MyEvent());
	}


}

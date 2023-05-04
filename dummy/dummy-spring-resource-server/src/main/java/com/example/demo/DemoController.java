package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/demo-controller")
public class DemoController {

	@GetMapping
	public ResponseEntity<String> sayHello(Principal principal) {
		return ResponseEntity.ok("Hello " + principal.getName()  + " from secured endpoint");
	}

}

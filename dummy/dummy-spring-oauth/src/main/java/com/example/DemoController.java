package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo-controller")
public class DemoController {

	@GetMapping
	public ResponseEntity<String> sayHello(@AuthenticationPrincipal User user) {
		SecurityContextHolder.getContext().getAuthentication();
		return ResponseEntity.ok("Hello " + user.getClientId()  + " from secured endpoint");
	}
}

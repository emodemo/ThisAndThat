package com.example.demo;

import com.example.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo-controller")
public class DemoController {

	@GetMapping
	public ResponseEntity<String> sayHello(@AuthenticationPrincipal User user) {
//		var name = principal.getName(); //Principal principal
//		var user = (User) ((Authentication) principal).getPrincipal();
		return ResponseEntity.ok("Hello " + user.getUsername()  + " from secured endpoint");
	}

}

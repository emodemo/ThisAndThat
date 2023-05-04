package com.example.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register( @RequestBody RegisterRequest request){
		var response = authenticationService.register(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate( @RequestBody AuthenticationRequest request){
		var response = authenticationService.authenticate(request);
		return ResponseEntity.ok(response);
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class RegisterRequest {
		private String username;
		private String password;
		private String email;
	}

	@Data
	static class AuthenticationResponse {
		private final String token;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class AuthenticationRequest {
		private String password;
		private String username;
	}


}

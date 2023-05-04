package com.example.auth;

import com.example.auth.AuthenticationController.AuthenticationRequest;
import com.example.auth.AuthenticationController.AuthenticationResponse;
import com.example.auth.AuthenticationController.RegisterRequest;
import com.example.jwt.JwtService;
import com.example.user.User;
import com.example.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest registerRequest){
		var encodedPass = passwordEncoder.encode(registerRequest.getPassword());
		var user = new User(registerRequest.getUsername(), registerRequest.getEmail(), encodedPass);
		userRepository.addUser(user);
		var jwtToken = jwtService.generateToken(user);
		return new AuthenticationResponse(jwtToken);
	}

	public AuthenticationResponse authenticate(AuthenticationRequest authNRequest){
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authNRequest.getUsername(), authNRequest.getPassword()));

		// actually the authenticationManager.authenticate performs the same steps thru the UserDetailsService
		// so either the code above or the commented one
		// var user = userRepository.findBy(authNRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("unknown user"));
		// var jwtToken = jwtService.generateToken(user);
		var jwtToken = jwtService.generateToken((UserDetails) authenticate.getPrincipal());
		return new AuthenticationResponse(jwtToken);
	}
}

package com.example;

import com.example.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

	private final UserRepository userRepository;

	@Bean
	public UserDetailsService userDetailsService(){
		return username -> userRepository.findBy(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Bean // returns the default impl -> ProviderManager which is a Proxy for different AuthNProviders like the DaoAuthenticationProvider (added by design)
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean // fetch user details, encode password, ... // DaoAuthenticationProvider out of the box for newer spring security
//	public AuthenticationProvider authenticationProvider(){
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(userDetailsService()); // from DB, or from LDAP, or elsewhere
//		provider.setPasswordEncoder(passwordEncoder());
//		return provider;
//	}




}

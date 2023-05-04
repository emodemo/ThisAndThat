package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().disable().httpBasic().disable()
				.authorizeHttpRequests().anyRequest().authenticated()
				.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.oauth2ResourceServer().jwt(); // BearerTokenAuthenticationFilter
		return http.build();
	}
	// using the converter one can change the Authentication Principal

//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//		var http = httpSecurity.csrf().disable().cors().disable().httpBasic().disable();
//		http = http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/auth/**").permitAll()
//				.anyRequest().authenticated());
//		// configure the resource server to use JWT authentication
//		// add converter from Jwt to User
//		http = http.oauth2ResourceServer((resourceServerConfigurer) ->
//				resourceServerConfigurer.jwt((jwtCustomizer) -> jwtCustomizer.jwtAuthenticationConverter(jwtToUserConverter)));
//		http = http.sessionManagement((sessionCustomizer) -> sessionCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		// configure the exception handling with bearer token entry point and access denied handler, both coming from ... server
//		http = http.exceptionHandling((exceptionCustomizer) -> exceptionCustomizer
//				.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
//				.accessDeniedHandler(new BearerTokenAccessDeniedHandler())
//		);
//
//		return http.build();
//	}




}

package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Resource Server Security Configuration
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfiguration {

	private final JwtUserAuthenticationConverter converter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http//.csrf().disable().cors().disable()
				.authorizeHttpRequests(registry -> registry
						.requestMatchers("/oauth2/**").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(config -> config
						.jwt(jwtConfig -> jwtConfig.jwtAuthenticationConverter(converter))) // BearerTokenAuthenticationFilter
				.build();
	}
}

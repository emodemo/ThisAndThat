package com.example;

import com.example.jwt.JwtAuthenticationFilter;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	//private final AuthenticationProvider authenticationProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		var http = httpSecurity.csrf().disable().cors().disable().httpBasic().disable();
		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/api/auth/**").permitAll()
				.anyRequest().authenticated());
		http.sessionManagement((sessionCustomizer) -> sessionCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		//http.authenticationProvider(authenticationProvider); // in case another provider is required
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // before the usernamePasswordAuthenticationFilter

		return http.build();
	}

	// @Bean // Alternative way to add a filter
	public FilterRegistrationBean<Filter> authFilterBean(JwtAuthenticationFilter jwtAuthenticationFilter) {
		FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(jwtAuthenticationFilter);
		registrationBean.setName("jwtAuthenticationFilter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(SecurityProperties.IGNORED_ORDER);
		return registrationBean;
	}
}

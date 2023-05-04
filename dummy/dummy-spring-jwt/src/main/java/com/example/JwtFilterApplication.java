package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * General schema:
 * HttpRequest --> JwtAuthFilter --> UserDetailsService --> check in the DB, or elsewhere
 *								 --> validateToken
 *								 --> update the SecurityContextHolder
 *								 --> pass to DispatcherServlet --> to the DemoController
 *
 * 3 endpoints: registration, authentication (to get new token), doBusiness + Bearer header
 * class User extends [api] UserDetails
 * [api]UserDetailsService, [api]AuthenticationProvider, 
 * [api]ProivederManager implements [api]AuthenticationManager
 *  --> defines how the SSFilters perform authentication
 *  --> loops thru the AuthenticationProviders.authenticate()
 * JWTAuthFilter extends [api]OncePerRequestFilter //
 * 
 * In the case of OAuth2 ResourceServer a JwtAuthProvider leverages a JwtDecoder and JwtAuthConverter - see the resource server example
 * another example here: https://github.com/bezkoder/spring-boot-refresh-token-jwt
 * on JavaEE see this: https://github.com/Posya/wiki/blob/master/best-practice-for-rest-token-based-authentication-with-jax-rs-and-jersey.adoc
 * and this: https://www.baeldung.com/java-ee-oauth2-implementation
 */
@SpringBootApplication
public class JwtFilterApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtFilterApplication.class, args);
	}

}

package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RegisteredClient -> a representation of registered user
 * RegisteredClientRepository
 * ProviderSettings -> Endpoints, Issuer
 * JWKSource -> RSA key pairs
 *
 * http://localhost:9000/.well-known/oauth-authorization-server
 * The /authorize endpoint is used for the Web Server OAuth Authentication Flow and User-Agent OAuth Authentication Flow.
 * The /token endpoint is used for the Username-Password OAuth Authentication Flow and the OAuth Refresh Token Process.
 */
@SpringBootApplication
public class AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizationServerApplication.class, args);
	}

}

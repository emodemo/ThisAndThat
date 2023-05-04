package com.example;

import lombok.Value;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.UUID;

@Value
public class User {
	String id;
	String clientId;
	String clientSecret;
	//String[] scopes;

	public RegisteredClient toRegisteredClient(){
		return RegisteredClient.withId(id)
				.clientId(clientId)
				.clientSecret(clientSecret)
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.scope("api.read")
				.scope("api.write")
				.build();
	}
}

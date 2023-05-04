package com.example;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class UserRepository implements RegisteredClientRepository {

	private static Map<String, RegisteredClient> users = new HashMap<>();
	static {
		RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("client")
				.clientSecret("{noop}secret")
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.build();
		users.put(client.getId(), client);
	}

	@Override
	public void save(RegisteredClient registeredClient) {
		users.put(registeredClient.getId(), registeredClient);
	}

	@Override
	public RegisteredClient findById(String id) {
		return users.get(id);
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		return users.values()
				.stream()
				.filter(user -> user.getClientId().equals(clientId))
				.findFirst()
				.orElse(null);
	}
}

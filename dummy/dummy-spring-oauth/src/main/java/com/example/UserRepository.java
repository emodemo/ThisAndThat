package com.example;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * The RegisteredClientRepository is used by the OAuth2 Authorization Server.
 * In this example `findBy()` is added to be used from the Resource Server too.
 */
@Repository
public class UserRepository implements RegisteredClientRepository {

	private static Map<String, User> users = new HashMap<>();
	static {
		String id = UUID.randomUUID().toString();
		users.put(id, new User(id, "client", "{noop}secret"));
	}

	@Override
	public void save(RegisteredClient registeredClient) {
		users.put(registeredClient.getId(), new User(registeredClient.getId(), registeredClient.getClientId(), registeredClient.getClientSecret()));
	}

	@Override
	public RegisteredClient findById(String id) {
		return users.get(id).toRegisteredClient();
	}

	@Override
	public RegisteredClient findByClientId(String clientId) {
		return users.values()
				.stream()
				.filter(user -> user.getClientId().equals(clientId))
				.findFirst()
				.map(User::toRegisteredClient)
				.orElse(null);
	}

	public Optional<User> findBy(String subjectId){
		return users.values()
				.stream()
				.filter(user -> user.getClientId().equals(subjectId))
				.findFirst();
	}
}

package com.example.user;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserRepository {

	private static Map<String, User> users = new HashMap<>();

	public Optional<User> findBy(String username){
		return Optional.ofNullable(users.get(username));
	}

	public void addUser(User user){
		users.put(user.getUsername(), user);
	}

}

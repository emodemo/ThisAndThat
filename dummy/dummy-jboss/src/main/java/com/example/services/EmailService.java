package com.example.services;

import javax.ejb.Stateless;

@Stateless
public class EmailService {

	public void send(String recipient, String message) {
		System.out.println(String.format("mail with message %s sent to %s", message, recipient));
	}
}

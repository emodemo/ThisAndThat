package org.example.services;

import org.springframework.stereotype.Service;


@Service
public class EmailService {

    public void send(String recipient, String message) {
        System.out.println(String.format("mail with message %s sent to %s", message, recipient));
    }
}

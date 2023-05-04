package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * expects the dummy-spring-auth-server to be run
 * example, but with older version of spring here
 * https://www.codeburps.com/post/spring-boot-oauth2-for-server-to-server-security
 * https://www.youtube.com/watch?v=EbzcnwS4q00
 */
@SpringBootApplication
public class ResourceServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceServerApplication.class, args);
	}

}

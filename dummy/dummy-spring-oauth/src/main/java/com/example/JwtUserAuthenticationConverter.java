package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

/**
 * Instead of JwtAuthenticationConverter
 * alternative soliutions: https://stackoverflow.com/questions/70626334/convert-jwt-authentication-principal-to-something-more-usable-in-spring
 */
@Component
@RequiredArgsConstructor
public class JwtUserAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private final UserRepository userRepository;

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		AbstractAuthenticationToken token = converter.convert(jwt);
		User user = userRepository.findBy(jwt.getSubject()).orElse(null);
		return new UsernamePasswordAuthenticationToken(user, user, token.getAuthorities());
	}
}

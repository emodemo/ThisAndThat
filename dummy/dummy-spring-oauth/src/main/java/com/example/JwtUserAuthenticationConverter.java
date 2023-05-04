package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

/**
 * Instead of JwtAuthenticationConverter
 */
@RequiredArgsConstructor
public class JwtToUserConverter implements Converter<Jwt, AbstractAuthenticationToken> {

	private final UserRepository userRepository;

	@Override
	public AbstractAuthenticationToken convert(Jwt jwt) {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		AbstractAuthenticationToken token = converter.convert(jwt);
		User user = userRepository.findBy(jwt.getSubject()).orElse(null);
		return new JwtUserAuthenticationToken(jwt, user, jwt, token.getAuthorities());
	}
}

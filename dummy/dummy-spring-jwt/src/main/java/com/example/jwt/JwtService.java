package com.example.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

	// in Vault
	private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

	public String extractUserName(String jwtToken){
		return extractClaim(jwtToken, Claims::getSubject);
	}

	public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
		final String username = extractUserName(jwtToken);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
	}

	public <T> T extractClaim(String jwtToken, Function<Claims, T> resolver){
		final Claims claims = extractAllClaims(jwtToken);
		return resolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails){
		return generateToken(userDetails, Map.of("aud", "bank-gateway", "iss", "bank-gateway"));
	}

	public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}

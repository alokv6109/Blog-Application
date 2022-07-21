package com.alok.project.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//import com.sun.org.apache.xpath.internal.functions.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	private static final long JWT_VALIDATION_TOKEN = 5*60*60; //5hrs in seconds
	
	private String secret = "alokverma";
	
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
		
	}

	private <T> T getClaimFromToken(String token, java.util.function.Function<Claims, T> claimResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
		
	}
	
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	
	//generator toek for user
	public String generateToken(UserDetails userDetails) {
		Map<String , Object> claims=  new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
		
	}
	
	/**
	 * while creating the token 
	 * 1) define claims of the token, like Issuer, expiration, Subbject and the ID
	 * 2) sign the JWT using the HS512 algorithm and the secret key
	 * 3) According to JWS compact serialzation (https://tools.ietf.org.....) compaction of the JWT to a url safe string
	 * @param claims
	 * @param username
	 * @return
	 */

	private String doGenerateToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDATION_TOKEN*100 )).signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
}

package com.example.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService
{
	private final String secret = "A5Twwdplq85oEFQbnJOvu0zC+QyLYmudvPu/K0DPjdaUlcd2yxMv2LH5zQnn28rk";
	@Autowired
	private TokenRepository tokenRepository;

	public String generateToken(String email)
	{
		return createToken(new HashMap<String, Object>(), email);
	}

	private String createToken(Map<String, Object> claims, String email)
	{
		return Jwts.builder()
				.claims(claims)
				.subject(email)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSignKey())
				.compact();
	}

	private boolean isTokenExpired(String jwtToken)
	{
		return extractExpiration(jwtToken).before(new Date());
	}

	public Date extractExpiration(String jwtToken)
	{
		return extractClaims(jwtToken, Claims::getExpiration);
	}

	public String extractUserEmail(String jwtToken)
	{
		return extractClaims(jwtToken, Claims::getSubject);
	}
	
	public <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver)
	{
		final Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String jwtToken)
	{
		return Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(jwtToken)
				.getPayload();
	}

	private SecretKey getSignKey()
	{
		byte[] key = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(key);
	}

	public String validate(String jwtToken)
	{
		Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(jwtToken);
		Token te = tokenRepository.findByToken(jwtToken)
				.get();
		if (!te.isValid() || isTokenExpired(jwtToken))
		{
			throw new RuntimeException("Invalid Token");
		}
		return te.getRole();
	}
}

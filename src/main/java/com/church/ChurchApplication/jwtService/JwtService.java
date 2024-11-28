package com.church.ChurchApplication.jwtService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

@Service
public class JwtService {
	
	private static final String SECRET = "TmV3U2VjcmV0S2V5Rm9ySldUU2lnbmluZ1B1cnBvc2VzMTIzNDU2Nzg="; // Base64 encoded secret key

	private final Key key;
	
	public JwtService() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
    }


	public String generateToken(String emailOrUsername) 
	{
		
		Map<String, Object> claims = new HashMap<>();

		 return Jwts.builder()
		            .setClaims(claims)
		            .setSubject(emailOrUsername)
		            .setIssuedAt(new Date(System.currentTimeMillis()))
		            .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365 * 3))
		            .signWith(key, SignatureAlgorithm.HS256)
		            .compact();
	}
	
	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	 public Claims extractClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }

	    public String extractUserName(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }

	    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimResolver.apply(claims);
	    }

	    private Claims extractAllClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }

	    public boolean validateToken(String token, UserDetails userDetails) {
	        final String userName = extractUserName(token);
	        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }

	    public boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }


	    private java.util.Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }


		public boolean validateToken(String token) {
			 try {
		            // Parse the token and validate the signature and expiration
		            Claims claims = Jwts.parser()
		                .setSigningKey(SECRET)
		                .parseClaimsJws(token)
		                .getBody();
		            
		            // Check if the token is expired
		            Date expiration = claims.getExpiration();
		            if (expiration != null && expiration.before(new Date())) {
		                return false; // Token is expired
		            }

		            // You can add more validations for claims if needed (e.g., issuer, audience)

		            return true; // Token is valid
		        } catch (JwtException | IllegalArgumentException e) {
		            // Token is invalid
		            return false;
		        }
		}

}

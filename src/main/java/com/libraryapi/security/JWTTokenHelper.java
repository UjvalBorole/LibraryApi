package com.libraryapi.security;



import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTTokenHelper {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours

    private String secret =  "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";
    
    private SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    
//    byte[] keyBytes = "your-256-bit-secret".getBytes(StandardCharsets.UTF_8);
//    SecretKey secret = Keys.hmacShaKeyFor(keyBytes);
    // Retrieve username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // For retrieving any information from token we need the secret key
    private Claims getAllClaimsFromToken(String token) {
        // return Jwts.parser().setSigningKey(secret.getBytes()).build().parseClaimsJws(token).getBody();
        return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    	
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate token for user
    public String generateToken(UserDetails userDetails) {
        
        return doGenerateToken(new HashMap<>(), userDetails);
    }

    // Compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
        .claims()
        .add(claims)
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
        .and()
        .signWith(secretKey, Jwts.SIG.HS256)
        .compact();
    }

    //used instead of secretKey in signwith
    // private Key getSignInKey() {
    //     byte[] keyBytes = this.secret.getBytes(StandardCharsets.UTF_8);
    //     return Keys.hmacShaKeyFor(keyBytes);
    // }

    // Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

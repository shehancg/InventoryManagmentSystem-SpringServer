package com.exe.inventorymsystemserver.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    /*@Value("${jwt.secret}") // Make sure to define this property in your application.properties or application.yml
    private String secretKey;*/

    @Value("${jwt.expirationMillis}") // Define token expiration time in milliseconds
    private long expirationMillis;

    public String generateJwt(Long userId, String username) {
        // Use Keys.secretKeyFor to generate a secure key for HS256
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseJwt(String token) {
        // Dynamically generate the key for parsing
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}

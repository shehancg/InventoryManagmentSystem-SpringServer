package com.exe.inventorymsystemserver.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.expirationMillis}")
    private long expirationMillis;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateJwt(Long userId, String username) {
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
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    /*public String extractUsername(String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody();
        return claims.getSubject();
    }*/

    public String extractUsername(String jwtToken) {
        // Remove the "Bearer " prefix
        String tokenWithoutBearer = jwtToken.replace("Bearer ", "");

        // Parse the JWT token without the "Bearer " prefix
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(tokenWithoutBearer).getBody();

        // Extract and return the subject (username)
        return claims.getSubject();
    }

}

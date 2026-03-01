package com.ironfit.backendmongo.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    private final Key key;
    private final int expirationMinutes;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-minutes}") int expirationMinutes
    ) {
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("security.jwt.secret debe tener al menos 32 caracteres");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
    }

    public String generarToken(String userId, String email, List<String> roles) {
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationMinutes * 60L);

        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .claim("roles", roles)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parsear(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public boolean esValido(String token) {
        try {
            parsear(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }
}
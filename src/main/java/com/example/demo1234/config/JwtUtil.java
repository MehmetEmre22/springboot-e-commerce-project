package com.example.demo1234.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkeymysecretkey"; // 🔥 En az 256 bit uzunluğunda olmalı
    private static final long EXPIRATION_TIME = 86400000; // 1 gün (milisaniye cinsinden)

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Kullanıcı kimliği
                .setIssuedAt(new Date()) // Üretilme zamanı
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Geçerlilik süresi
                .signWith(key, SignatureAlgorithm.HS256) // İmzalama
                .compact();
    }

    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }
}

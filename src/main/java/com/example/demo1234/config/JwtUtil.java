package com.example.demo1234.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "d1aecf5e0f581df5f3d0ab906b8806519328533fa0426efddd64bd38a66457fcb7be87012ac3ea24f272932b03814826d7a4313e75ac1e7776d7ab117e310bf5374c3777af2e8765137369f1bafcc9526c0f9129004fec272e64c19b362e307ca5614da60007a9c4159f28f06dde00571dfe138f834cc31989ebcc38e9b4c7e59b63fb3879fcd2be476fec564b179bb3663828d3a046cfa5973608b2ade328180b933d342300600acbd54601a8f5e436bca95fc2362ffc4925565b1d4b6ec920482dfb2cb6255304f9e0049b8fc9239093e65705f47a62d65b241224ccc5ea43e142a74dcb38bea2769d9a9e4a15da29cdc1abf52fb23322b0751cd4055dd836"; // 256 bit uzunluğunda olmalı
    private static final long EXPIRATION_TIME = 86400000; // 1 gün

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // 🔥 Şu an 2 parametre alıyor: username + role
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role) // 🎯 Rolü ekliyoruz
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parseToken(token).getBody().getSubject();
    }

    public String extractRole(String token) {
        return parseToken(token).getBody().get("role", String.class);
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

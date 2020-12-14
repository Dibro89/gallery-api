package me.dibro.gallery.api.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

public class JwtHelper {

    private static final SecretKey KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private JwtHelper() {
    }

    public static String buildJwt(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .signWith(KEY)
                .compact();
    }

    public static String parseJwt(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
}

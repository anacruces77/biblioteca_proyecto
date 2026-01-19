package com.example.biblioteca.security;

import com.example.biblioteca.entity.Usuario;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {


    private final String SECRET_KEY = "EstaEsUnaClaveSuperSecretaYMuyLargaParaQueJJWTNoSeQueje1234567890";
    private final long EXPIRATION_MS = 1000 * 60 * 60 * 10; // 10 horas

    // ---------------- Claims genéricos ----------------
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // ---------------- JWT con Roles ----------------
    public String generateToken(Usuario usuario) {
        // CAMBIO 1: Asegurarnos de que el rol guardado en el token tenga el prefijo ROLE_
        String roleWithPrefix = usuario.getRol().name();
        if (!roleWithPrefix.startsWith("ROLE_")) {
            roleWithPrefix = "ROLE_" + roleWithPrefix;
        }

        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("role", roleWithPrefix) // Ahora guardará "ROLE_ADMIN" en lugar de "ADMIN"
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> (String) claims.get("role"));
    }

}

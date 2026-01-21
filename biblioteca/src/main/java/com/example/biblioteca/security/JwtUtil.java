package com.example.biblioteca.security;

import com.example.biblioteca.entity.Usuario;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Clave secreta para firmar los tokens (debe ser muy segura)
    private final String SECRET_KEY = "EstaEsUnaClaveSuperSecretaYMuyLargaParaQueJJWTNoSeQueje1234567890";
    private final long EXPIRATION_MS = 1000 * 60 * 60 * 10; // 10 horas

    // Extrae el "subject" del token, en este caso es el email
    // ---------------- Claims genéricos ----------------
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Metodo genérico para sacar cualquier dato (claim) del token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Descodifica el token usando la clave secreta para leer su contenido
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Verifica que el token pertenezca al usuario y no haya caducado
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    // Genera el token añadiendo el rol del usuario como una propiedad extra (claim)
    // ---------------- JWT con Roles ----------------
    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("role", "ROLE_" + usuario.getRol().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Extrae específicamente el rol guardado en el token
    public String extractRole(String token) {
        return extractClaim(token, claims -> (String) claims.get("role"));
    }

}

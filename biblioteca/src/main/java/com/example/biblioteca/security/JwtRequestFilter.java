package com.example.biblioteca.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import com.example.biblioteca.Services.UsuarioService;
import com.example.biblioteca.entity.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // 1. Extraer el token del encabezado
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {
                logger.error("Error al extraer el username del token: " + e.getMessage());
            }
        }

        // 2. Validar el usuario y establecer la seguridad
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Usuario usuario = usuarioService.getUsuarioByEmail(username).orElse(null);

            if (usuario != null && jwtUtil.validateToken(jwt, usuario.getEmail())) {

                // --- BLOQUE CLAVE: ASIGNACIÓN DE ROLES ---
                // Extraemos el rol del JWT (ej: "ROLE_ADMIN")
                String roleName = jwtUtil.extractRole(jwt);

                // Creamos la autoridad que Spring Security puede entender
                org.springframework.security.core.authority.SimpleGrantedAuthority authority =
                        new org.springframework.security.core.authority.SimpleGrantedAuthority(roleName);

                // Creamos el token de autenticación PASANDO LA LISTA DE AUTORIDADES (en lugar de null)
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                usuario,
                                null,
                                java.util.Collections.singletonList(authority)
                        );
                // ------------------------------------------

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Seteamos la autenticación en el contexto global de la aplicación
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 3. Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }

}

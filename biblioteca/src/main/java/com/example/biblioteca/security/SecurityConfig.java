package com.example.biblioteca.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity // permite usar @PreAuthorize en los endpoints // Permite usar @PreAuthorize("hasRole('ADMIN')") directamente en los métodos del Controller
public class SecurityConfig {


    private final JwtRequestFilter jwtRequestFilter;



    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desactivamos CSRF (común en APIs con tokens)
                .authorizeHttpRequests(auth -> auth
                        // 1. Rutas públicas (Login, Registro y Consola H2)
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // 2. REGLAS POR ROL
                        // Nota: Si usas @PreAuthorize en los controladores,
                        // estas líneas aquí son opcionales pero refuerzan la seguridad.
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")

                        // 3. Todo lo demas requiere estar logueado
                        .anyRequest().authenticated()
                )

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Si alguien intenta entrar a un sitio protegido sin token, recibe un 401
                            // Cuando no hay token o la autenticación falla, devolvemos 401
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: No autorizado");
                        })
                )
                // Permite que la consola H2 se vea correctamente en el navegador
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                // Añade nuestro filtro JWT antes del filtro estándar de usuario/contraseña
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    // Define el algoritmo de hash para las contraseñas (BCrypt es el estándar actual)
    // Bean para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    // Configuración necesaria para que Spring gestione el proceso de autenticación
    // Bean de AuthenticationManager necesario para login con JWT
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }






    /*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Nueva sintaxis para desactivar CSRF
                .csrf(csrf -> csrf.disable())

                // Nueva sintaxis para autorizar peticiones
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Por ahora, la seguridad la he apagado para poder realizar los test antes
                )

                // ESTO ES LO QUE ARREGLA EL ACCESO A H2-CONSOLE
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }

    */

}

package com.example.biblioteca.security;

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
@EnableMethodSecurity // permite usar @PreAuthorize en los endpoints
public class SecurityConfig {


    private final JwtRequestFilter jwtRequestFilter;



    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Rutas públicas
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()

                        // 2. REGLAS POR ROL (Esto es lo que te falta)
                        // Solo los ADMIN pueden hacer POST, PUT y DELETE en usuarios
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
                        .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")

                        // 3. Todo lo demás requiere estar logueado (como ver la lista de usuarios)
                        .anyRequest().authenticated()
                )
                // Manejo de la consola de H2
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                // FILTRO JWT: Solo añadirlo UNA VEZ
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }




    // Bean para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




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

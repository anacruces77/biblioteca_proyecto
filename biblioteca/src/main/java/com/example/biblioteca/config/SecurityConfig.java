package com.example.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Nueva sintaxis para desactivar CSRF
                .csrf(csrf -> csrf.disable())

                // Nueva sintaxis para autorizar peticiones
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                // ESTO ES LO QUE ARREGLA EL ACCESO A H2-CONSOLE
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                );

        return http.build();
    }
}

package com.example.biblioteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Desactiva CSRF para poder probar POST desde Bruno
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // permite todas las rutas
        return http.build();
    }
}

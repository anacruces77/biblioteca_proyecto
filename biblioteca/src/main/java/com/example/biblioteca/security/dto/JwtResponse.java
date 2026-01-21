package com.example.biblioteca.security.dto;

// El token es final porque una vez creado para la respuesta no debe cambiar
public class JwtResponse {

    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

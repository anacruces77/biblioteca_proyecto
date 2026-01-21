package com.example.biblioteca.security.dto;

public class JwtRequest {

    private String email;
    private String password;

    // Constructor vacío necesario para que Jackson pueda deserializar el JSON
    public JwtRequest() {}

    // Constructor para inicializar con datos
    public JwtRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

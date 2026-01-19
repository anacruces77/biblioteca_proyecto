package com.example.biblioteca.exception;

// Extiende de RuntimeException para crear la excepcion personalizada
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}

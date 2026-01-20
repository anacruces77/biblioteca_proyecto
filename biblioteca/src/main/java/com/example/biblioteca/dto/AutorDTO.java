package com.example.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AutorDTO {

    // no permite null, "" ni " "
    // @NotBlank asegura que el texto no sea nulo ni solo espacios en blanco
    @NotBlank(message = "El nombre del autor es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    public AutorDTO() {
    }

    // Getter y setter: permiten acceder y modificar el nombre de forma segura
    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

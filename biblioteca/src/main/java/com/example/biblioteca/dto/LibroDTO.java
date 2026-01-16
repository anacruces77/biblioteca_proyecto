package com.example.biblioteca.dto;

import jakarta.validation.constraints.*;

public class LibroDTO {
    @NotBlank(message = "El título no puede estar vacío")
    @Size(min = 2, max = 150, message = "El título debe tener entre 2 y 150 caracteres")
    private String titulo;

    @NotBlank(message = "El ISBN es obligatorio")
    @Pattern(
            regexp = "^[0-9\\-]{10,17}$",
            message = "El ISBN tiene un formato inválido"
    )
    private String isbn;

    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1450, message = "El año no puede ser anterior a la imprenta")
    @Max(value = 2100, message = "El año no puede ser futuro")
    private Integer anioPublicacion;

    @NotNull(message = "El autor es obligatorio")
    private Long autorId;


    // Getters y setters

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

}

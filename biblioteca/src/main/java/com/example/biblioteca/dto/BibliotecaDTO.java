package com.example.biblioteca.dto;

import com.example.biblioteca.entity.EstadoLibro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// De entrada
public class BibliotecaDTO {

    @NotNull(message = "El estado del libro es obligatorio")
    private EstadoLibro estado;

    @NotNull(message = "El id del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El id del libro es obligatorio")
    private Long libroId;

    // --- getters y setters ---

    public EstadoLibro getEstado() {
        return estado;
    }

    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getLibroId() {
        return libroId;
    }

    public void setLibroId(Long libroId) {
        this.libroId = libroId;
    }


}

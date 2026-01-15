package com.example.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table( name = "bibliotecas")
public class Biblioteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Estado de lectura del libro
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLibro estado;

    // Relación con Usuario (N:1) (propietario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "usuario_id", nullable = false)
    private Usuario usuario;


    // Relación con Libro (N:1) (propietario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "libro_id", nullable = false)
    private Libro libro;


}

package com.example.biblioteca.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer puntuacion;

    @Column(length = 1000)
    private String comentario;

    // Relación N:1 con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "usuario_id", nullable = false)
    private Usuario usuario;


    // Relación N:1 con Libro (propietario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "libro_id", nullable = false)
    private Libro libro;



}

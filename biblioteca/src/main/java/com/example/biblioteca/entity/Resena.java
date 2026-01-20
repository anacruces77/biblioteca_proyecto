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

    @Column(length = 1000) // Permite un comentario más largo (hasta 1000 caracteres)
    private String comentario;

    // Relación N:1 con Usuario Define qué usuario escribió esta reseña
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "usuario_id", nullable = false)
    private Usuario usuario;


    // Relación N:1 con Libro (propietario)  Define sobre qué libro es esta reseña
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "libro_id", nullable = false)
    private Libro libro;



}

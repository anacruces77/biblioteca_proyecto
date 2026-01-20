package com.example.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(unique = true) // No pueden existir dos libros con el mismo ISBN
    private String isbn;

    private Integer anioPublicacion;

    // Relación N:1 con Autor (propietario) Muchos libros pueden ser escritos por el mismo Autor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "autor_id", nullable = false)
    private Autor autor;


    // Relación 1:N con Reseña Si se borra el libro, se borran sus reseñas (CascadeType.ALL)
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Resena> resenas;

    // Relación 1:N con Biblioteca (N:M transformada) // Conexión con la tabla intermedia 'Biblioteca'
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Biblioteca> bibliotecas;

}

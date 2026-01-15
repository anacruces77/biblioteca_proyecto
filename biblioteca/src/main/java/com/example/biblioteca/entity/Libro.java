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

    @Column(unique = true)
    private String isbn;

    private Integer anioPublicacion;

    // Relación N:1 con Autor (propietario)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "autor_id", nullable = false)
    private Autor autor;


    // Relación 1:N con Reseña
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Resena> resenas;

    // Relación 1:N con Biblioteca (N:M transformada)
    @OneToMany(mappedBy = "libro", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Biblioteca> bibliotecas;

}

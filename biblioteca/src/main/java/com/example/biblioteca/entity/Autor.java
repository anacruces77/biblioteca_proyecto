package com.example.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Relación 1:N con Libro (lado inverso)
    @OneToMany( mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Libro> libros;

}

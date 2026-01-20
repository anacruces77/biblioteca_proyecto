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

    // Guarda el valor del Enum como una cadena de texto (String) en la base de datos
    // Estado de lectura del libro
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLibro estado;

    // Relación con Usuario (N:1) (propietario) Varias entradas en biblioteca pertenecen a un solo Usuario
    @ManyToOne(fetch = FetchType.LAZY) // LAZY: Solo carga los datos del usuario si realmente se necesitan
    @JoinColumn( name = "usuario_id", nullable = false) // Nombre de la columna de la llave foránea
    private Usuario usuario;


    // Relación con Libro (N:1) (propietario) Varias entradas en biblioteca pueden referenciar al mismo Libro
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "libro_id", nullable = false)
    private Libro libro;


}

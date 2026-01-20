package com.example.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity // Define que esta clase se mapeará como una tabla en la base de datos
@Data   // Genera automáticamente Getters, Setters, toString y equals mediante Lombok
@Table(name = "autores") // Especifica el nombre real de la tabla en SQL
public class Autor {

    @Id // Marca este campo como la clave primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El ID se genera automáticamente (autoincremental)
    private Long id;

    @Column(nullable = false, length = 100) // Campo obligatorio y con límite de caracteres
    private String nombre;

    // Relación 1:N (Un autor tiene muchos libros). 'mappedBy' indica que la relación se gestiona en la clase Libro
    @OneToMany( mappedBy = "autor", cascade = CascadeType.ALL)
    @JsonIgnore // Evita que al pedir un autor se carguen infinitamente sus libros en el JSON
    private List<Libro> libros;

}
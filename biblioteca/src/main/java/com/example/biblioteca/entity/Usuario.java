package com.example.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name= "usuarios")
public class Usuario {

    // Clave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Relación 1:1 con Perfil
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Perfil perfil;






}

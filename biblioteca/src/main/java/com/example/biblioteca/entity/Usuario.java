package com.example.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    // Relación 1:1 con Perfil (lado inverso)
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Perfil perfil;

    // Relación 1:N con Reseñas (lado inverso)
    @OneToMany( mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Resena> resenas;

    // Relación 1:N con Biblioteca (N:M transformada) (lado inverso)
    @OneToMany( mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Biblioteca> bibliotecas;



// N:M con Rol (para seguridad, opcional)
    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(
    //     name = "usuario_rol",
    //     joinColumns = @JoinColumn(name = "usuario_id"),
    //     inverseJoinColumns = @JoinColumn(name = "rol_id")
    // )
    // private List<Rol> roles;




}

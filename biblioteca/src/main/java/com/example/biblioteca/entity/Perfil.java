package com.example.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String avatar;

    // Relación 1:1 con Usuario (propietario) Cada perfil pertenece a un único usuario.
    @OneToOne
    @JoinColumn( name = "usuario_id", nullable = false, unique = true) // El ID del usuario no se puede repetir aquí
    @JsonBackReference // Evita la recursividad infinita: el perfil no serializa al usuario de vuelta
    private Usuario usuario;


}

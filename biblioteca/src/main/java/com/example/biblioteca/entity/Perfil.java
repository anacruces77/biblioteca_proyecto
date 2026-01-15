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

    // Relación 1:1 con Usuario (propietario)
    @OneToOne
    @JoinColumn( name = "usuario_id", nullable = false, unique = true)
    @JsonBackReference
    private Usuario usuario;


}

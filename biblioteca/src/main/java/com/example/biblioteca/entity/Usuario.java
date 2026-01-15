package com.example.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

// Para las validaciones
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Para constructor, getter y setter automático
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

    // No puede ser null ni vacío
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    // No puede ser null y debe tener formato correcto de email
    @NotBlank( message = "El email es obligatorio")
    @Email( message = "Formato de email inválido")
    @Column(nullable = false, unique = true)
    private String email;

    // Mínimo 6 caracteres
    @NotBlank( message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(nullable = false)
    private String password;

    // Relación 1:1 con Perfil (lado inverso)
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Perfil perfil;

    // Relación 1:N con Reseñas (lado inverso)
    @OneToMany( mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Resena> resenas;

    // Relación 1:N con Biblioteca (N:M transformada) (lado inverso)
    @OneToMany( mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
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

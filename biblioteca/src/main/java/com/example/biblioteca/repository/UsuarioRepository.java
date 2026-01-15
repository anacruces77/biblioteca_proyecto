package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JpaRepository incluye automáticamente métodos básicos como sa(ve(), delete(), findAll(), findById(), delete)
// Usuario --> Nombre de la entidad que va a gestionar
// Long --> tipo de dato que tiene la clave primaria (ID) de ese usuario
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Consulta personalizada: Buscar usuario por email
    Optional<Usuario> findByEmail(String email);

    /* Optional<Usuario>, Devuelve un Optional, es como una caja: la abres y puede que dentro esté el usuario
     o puede que esté vacía. Esto te obliga a gestionar el caso de que el usuario no exista,
     evitando el error NullPointerException. */

}

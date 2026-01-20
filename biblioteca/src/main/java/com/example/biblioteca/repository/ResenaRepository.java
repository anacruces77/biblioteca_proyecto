package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository incluye automáticamente métodos básicos como sa(ve(), delete(), findAll(), findById(), delete)
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    // Recupera todos los comentarios/puntuaciones asociados a un libro concreto
    // Buscar todas las reseñas de un libro
    List<Resena> findByLibroId(Long libroId);

    // Recupera todo el historial de reseñas escrito por un usuario específico
    // Buscar todas las reseñas de un usuario
    List<Resena> findByUsuarioId(Long usuarioId);

    // Si no tiene ninguna reseña nos devolverá una lista vacía [], nunca un null.
}

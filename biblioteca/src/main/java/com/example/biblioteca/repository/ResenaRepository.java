package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {

    // Buscar todas las reseñas de un libro
    List<Resena> findByLibroId(Long libroId);

    // Buscar todas las reseñas de un usuario
    List<Resena> findByUsuarioId(Long usuarioId);

    // Si no tiene ninguna reseña nos devolverá una lista vacía []
}

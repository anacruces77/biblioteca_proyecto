package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JpaRepository incluye automáticamente métodos básicos como sa(ve(), delete(), findAll(), findById(), delete)
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Buscar libro por isbn
    Optional<Libro> findByIsbn(String isbn);

}

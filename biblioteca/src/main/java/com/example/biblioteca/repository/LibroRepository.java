package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Buscar libro por isbn
    Optional<Libro> findByIsbn(String isbn);

}

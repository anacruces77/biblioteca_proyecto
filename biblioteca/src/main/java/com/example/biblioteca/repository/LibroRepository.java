package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//JpaRepository incluye automáticamente métodos básicos como (save(), delete(), findAll(), findById(), delete)
public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Devuelve un Optional por si el ISBN buscado no existe en la base de datos
    // Buscar libro por isbn
    Optional<Libro> findByIsbn(String isbn);

}

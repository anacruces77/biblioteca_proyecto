package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Biblioteca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository incluye automáticamente métodos básicos como sa(ve(), delete(), findAll(), findById(), delete)
public interface BibliotecaRepository extends JpaRepository<Biblioteca, Long> {

    // Todos los libros de un usuario
    List<Biblioteca> findByUsuarioId(Long usuarioId);

    // Todos los usuarios que tienen un libro
    List<Biblioteca> findByLibroId(Long libroId);

}

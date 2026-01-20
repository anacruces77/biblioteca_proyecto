package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

// Al extender JpaRepository, Spring crea automáticamente los métodos CRUD (Crear, Leer, Actualizar, Borrar)
//JpaRepository incluye automáticamente métodos básicos como sa(ve(), delete(), findAll(), findById(), delete)
public interface AutorRepository extends JpaRepository<Autor, Long> {
// No necesita métodos extra; ya hereda findAll(), save(), deleteById(), etc.
}

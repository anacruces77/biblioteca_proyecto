package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository incluye automáticamente métodos básicos como sa(ve(), delete(), findAll(), findById(), delete)
public interface AutorRepository extends JpaRepository<Autor, Long> {

}

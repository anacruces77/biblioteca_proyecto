package com.example.biblioteca.repository;

import com.example.biblioteca.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

// Gestiona las operaciones de persistencia para la entidad Perfil
//JpaRepository incluye automáticamente métodos básicos como sa(ve(), delete(), findAll(), findById(), delete)
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}

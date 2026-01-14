package com.example.biblioteca.Services;

import com.example.biblioteca.entity.Resena;
import com.example.biblioteca.repository.ResenaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    private final ResenaRepository resenaRepository;

    public ResenaService (ResenaRepository resenaRepository){
        // Inyectamos el repositorio de Reseña
        this.resenaRepository = resenaRepository;
    }


    // Guardar o actualizar reseña
    public Resena saveResena(Resena resena) {
        return resenaRepository.save(resena);
    }

    // Listar todas las reseñas
    public List<Resena> getAllResenas() {
        return resenaRepository.findAll();
    }

    // Buscar reseña por ID
    public Optional<Resena> getResenaById(Long id) {
        return resenaRepository.findById(id);
    }

    // Listar reseñas por Usuario
    public List<Resena> getResenasByUsuario (Long usuarioId) {
        return resenaRepository.findByUsuarioId(usuarioId);
    }


    // Listar reseñas por Libro
    public List<Resena> getResenasByLibro (Long libroId) {
        return resenaRepository.findByLibroId(libroId);
    }


    // Eliminar reseña por ID
    public void deleteResena(Long id) {
        resenaRepository.deleteById(id);
    }


}

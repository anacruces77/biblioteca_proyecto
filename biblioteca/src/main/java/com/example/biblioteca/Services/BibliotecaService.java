package com.example.biblioteca.Services;


import com.example.biblioteca.entity.Biblioteca;
import com.example.biblioteca.repository.BibliotecaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;

    // Inyectamos el repositorio de Reseña
    public BibliotecaService(BibliotecaRepository bibliotecaRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
    }

    // Guardar o actualizar Biblioteca
    public Biblioteca saveBiblioteca (Biblioteca b) {
        return bibliotecaRepository.save(b);
    }

    // Listar todas las bibliotecas
    public List<Biblioteca> getAllBibliotecas() {
        return bibliotecaRepository.findAll();
    }

    // Buscar Biblioteca por ID
    public Optional<Biblioteca> getBibliotecaById(Long id) {
        return bibliotecaRepository.findById(id);
    }


    // Listar bibliotecas por usuario
    public List<Biblioteca> getBibliotecasByUsuario(Long usuarioId) {
        return bibliotecaRepository.findByUsuarioId(usuarioId);
    }


    // Listar bibliotecas por libro
    public List<Biblioteca> getBibliotecasByLibro(Long libroId) {
        return bibliotecaRepository.findByLibroId(libroId);
    }



    // Eliminar Biblioteca por ID
    public void deleteBiblioteca(Long id) {
        bibliotecaRepository.deleteById(id);
    }



}

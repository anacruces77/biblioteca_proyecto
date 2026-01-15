package com.example.biblioteca.Services;


import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    // Inyectamos repositorios
    public LibroService ( LibroRepository libroRepository,
                          AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    // Guardar o actualizar libro
    public Libro saveLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    // Listar todos los libros
    public List<Libro> getAllLibros() {
        return libroRepository.findAll();
    }

    // Buscar libro por ID
    public Optional<Libro> getLibroById(Long id) {
        return libroRepository.findById(id);
    }

    // Buscar libro por ISBN
    public Optional<Libro> getLibroByIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn);
    }

    // Buscar autor por ID
    public Autor getAutorById(Long autorId) {
        return autorRepository.findById(autorId)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado"));
    }

    // Eliminar libro por ID
    public void deleteLibro(Long id) {
        libroRepository.deleteById(id);
    }



}

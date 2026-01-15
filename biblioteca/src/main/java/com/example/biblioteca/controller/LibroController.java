package com.example.biblioteca.controller;


import com.example.biblioteca.Services.LibroService;
import com.example.biblioteca.dto.LibroDTO;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Libro;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    // Inyectamos el service
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    // GET /api/libros → listar libros
    @GetMapping
    public List<Libro> getAllLibros() {
        return libroService.getAllLibros();
    }

    // GET /api/libros/{id} → libro por id
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        Optional<Libro> libro = libroService.getLibroById(id);
        return libro.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/libros → crear libro
    @PostMapping
    public ResponseEntity<Libro> createLibro(@Valid @RequestBody LibroDTO dto) {

        Autor autor = libroService.getAutorById(dto.getAutorId());

        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setIsbn(dto.getIsbn());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setAutor(autor);

        Libro saved = libroService.saveLibro(libro);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // DELETE /api/libros/{id} → eliminar libro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        libroService.deleteLibro(id);
        return ResponseEntity.noContent().build();
    }


}

package com.example.biblioteca.controller;

import com.example.biblioteca.Services.AutorService;
import com.example.biblioteca.entity.Autor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    // Inyectamos el service
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // GET /api/autores → listar autores
    @GetMapping
    public List<Autor> getAllAutores() {
        return autorService.getAllAutores();
    }

    // GET /api/autores/{id} → autor por id
    @GetMapping("/{id}")
    public ResponseEntity<Autor> getAutorById(@PathVariable Long id) {
        Optional<Autor> autor = autorService.getAutorById(id);
        return autor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/autores → crear autor
    @PostMapping
    public ResponseEntity<Autor> createAutor(@RequestBody Autor autor) {
        Autor saved = autorService.saveAutor(autor);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // DELETE /api/autores/{id} → eliminar autor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }

}

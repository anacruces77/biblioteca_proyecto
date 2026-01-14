package com.example.biblioteca.controller;

import com.example.biblioteca.Services.BibliotecaService;
import com.example.biblioteca.entity.Biblioteca;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/biblioteca")
public class BibliotecaController {

    private final BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    // GET /api/biblioteca → listar biblioteca
    @GetMapping
    public List<Biblioteca> getAllBiblioteca() {
        return bibliotecaService.getAllBibliotecas();
    }

    // GET /api/biblioteca/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Biblioteca> getBibliotecaById(@PathVariable Long id) {
        Optional<Biblioteca> biblioteca = bibliotecaService.getBibliotecaById(id);
        return biblioteca.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/biblioteca → añadir libro a biblioteca
    @PostMapping
    public ResponseEntity<Biblioteca> createBiblioteca(@RequestBody Biblioteca biblioteca) {
        Biblioteca saved = bibliotecaService.saveBiblioteca(biblioteca);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // DELETE /api/biblioteca/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBiblioteca(@PathVariable Long id) {
        bibliotecaService.deleteBiblioteca(id);
        return ResponseEntity.noContent().build();
    }
}

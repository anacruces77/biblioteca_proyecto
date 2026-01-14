package com.example.biblioteca.controller;


import com.example.biblioteca.Services.ResenaService;
import com.example.biblioteca.entity.Resena;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    private final ResenaService resenaService;

    // Inyectar servicio
    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    // GET /api/resenas → listar reseñas
    @GetMapping
    public List<Resena> getAllResenas() {
        return resenaService.getAllResenas();
    }

    // GET /api/resenas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Resena> getResenaById(@PathVariable Long id) {
        Optional<Resena> resena = resenaService.getResenaById(id);
        return resena.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/resenas → crear reseña
    @PostMapping
    public ResponseEntity<Resena> createResena(@RequestBody Resena resena) {
        Resena saved = resenaService.saveResena(resena);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // DELETE /api/resenas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResena(@PathVariable Long id) {
        resenaService.deleteResena(id);
        return ResponseEntity.noContent().build();
    }


}

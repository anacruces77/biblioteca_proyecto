package com.example.biblioteca.controller;


import com.example.biblioteca.Services.ResenaService;
import com.example.biblioteca.dto.ResenaDTO;
import com.example.biblioteca.entity.Resena;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    // Usuarios pueden ver todas las reseñas
    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<Resena> getAllResenas() {
        return resenaService.getAllResenas();
    }

    // GET /api/resenas/{id}
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Resena> getResenaById(@PathVariable Long id) {
        Optional<Resena> resena = resenaService.getResenaById(id);
        return resena.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/resenas → crear reseña
    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> createResena(@jakarta.validation.Valid @RequestBody ResenaDTO resenaDTO) {
        resenaService.saveResenaDesdeDTO(resenaDTO);
        // Al devolver un String, Spring no intenta procesar las relaciones Usuario/Libro
        return new ResponseEntity<>("¡Éxito! Reseña guardada correctamente en la base de datos", HttpStatus.CREATED);
    }

    // DELETE /api/resenas/{id}
    // Solo admins pueden eliminar reseñas
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteResena(@PathVariable Long id) {
        resenaService.deleteResena(id);
        return ResponseEntity.noContent().build();
    }


}

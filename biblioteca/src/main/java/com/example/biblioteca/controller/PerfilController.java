package com.example.biblioteca.controller;

import com.example.biblioteca.Services.PerfilService;
import com.example.biblioteca.dto.PerfilDTO;
import com.example.biblioteca.entity.Perfil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/perfiles")
public class PerfilController {

    private final PerfilService perfilService;

    // Inyectamos el Servicio de Perfil
    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }



    // GET /api/perfiles → listar todos los perfiles
    // Ver perfiles → admins
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Perfil> getAllPerfiles() {
        return perfilService.getAllPerfiles();
    }

    // GET /api/perfiles/{id} → perfil por id
    // Ver perfil por id → admin o dueño del perfil
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.id")
    public ResponseEntity<Perfil> getPerfilById(@PathVariable Long id) {
        Optional<Perfil> perfil = perfilService.getPerfilById(id);
        return perfil.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/perfiles → crear perfil
    // Crear perfil → cualquier usuario logueado
    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Perfil> createPerfil(@Valid @RequestBody PerfilDTO dto) { // Recibe DTO
        Perfil saved = perfilService.saveFromDTO(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // PUT /api/perfiles/{id} → actualizar perfil
    // Actualizar perfil → admin o dueño
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.id")
    public ResponseEntity<Perfil> updatePerfil(@PathVariable Long id,
                                               @Valid @RequestBody Perfil perfil) {
        Optional<Perfil> existing = perfilService.getPerfilById(id);
        if (existing.isPresent()) {
            perfil.setId(id); // aseguramos actualización
            Perfil updated = perfilService.savePerfil(perfil);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/perfiles/{id} → eliminar perfil
    // Eliminar perfil → solo admins
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        perfilService.deletePerfil(id);
        return ResponseEntity.noContent().build();
    }


}

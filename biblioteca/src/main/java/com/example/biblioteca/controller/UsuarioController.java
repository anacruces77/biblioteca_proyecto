package com.example.biblioteca.controller;

import com.example.biblioteca.Services.UsuarioService;
import com.example.biblioteca.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Indica que esta clase responde con JSON
@RestController
// Ruta base para todos los endpoints de Usuario
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /api/usuarios → todos los usuarios
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // GET /api/usuarios/{id} → usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/usuarios → crear usuario
    @PostMapping
    public ResponseEntity<Usuario> createUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario saved = usuarioService.saveUsuario(usuario);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // PUT /api/usuarios/{id} → actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id,
                                                 @Valid @RequestBody Usuario usuario) {
        Optional<Usuario> existing = usuarioService.getUsuarioById(id);
        if (existing.isPresent()) {
            usuario.setId(id); // aseguramos que se actualice
            Usuario updated = usuarioService.saveUsuario(usuario);
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/usuarios/{id} → eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

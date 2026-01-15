package com.example.biblioteca.controller;

import com.example.biblioteca.Services.UsuarioService;
import com.example.biblioteca.dto.PerfilDTO;
import com.example.biblioteca.dto.UsuarioDTO;
import com.example.biblioteca.entity.Perfil;
import com.example.biblioteca.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    // Usando DTO
    // POST /api/usuarios → crear usuario
    @PostMapping
    public ResponseEntity<?> createUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        // Convertimos DTO a entidad
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword(usuarioDTO.getPassword());

        if(usuarioDTO.getPerfil() != null){
            PerfilDTO perfilDTO = usuarioDTO.getPerfil();
            Perfil perfil = new Perfil();
            perfil.setNickname(perfilDTO.getNickname());
            perfil.setAvatar(perfilDTO.getAvatar());
            // asignamos relación bidireccional
            perfil.setUsuario(usuario);
            usuario.setPerfil(perfil);
        }

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

package com.example.biblioteca.controller;

import com.example.biblioteca.Services.UsuarioService;
import com.example.biblioteca.dto.PerfilDTO;
import com.example.biblioteca.dto.UsuarioDTO;
import com.example.biblioteca.entity.Perfil;
import com.example.biblioteca.entity.Rol;
import com.example.biblioteca.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // Solo admins pueden ver todos los usuarios
    // GET /api/usuarios → todos los usuarios
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Usuario> getAllUsuarios() {
        return usuarioService.getAllUsuarios();
    }

    // Cualquier usuario logueado puede ver su propio perfil
    // GET /api/usuarios/{id} → usuario por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public Usuario getUsuarioById(@PathVariable Long id) {
        return usuarioService.getUsuarioByIdOrThrow(id);
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
        usuario.setRol(Rol.ROLE_USER); //Rol por defecto

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



    // Solo admins pueden eliminar usuarios
    // DELETE /api/usuarios/{id} → eliminar usuario
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

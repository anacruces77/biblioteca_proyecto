package com.example.biblioteca.Services;

import com.example.biblioteca.entity.Rol;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.dto.UsuarioDTO;
import com.example.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import com.example.biblioteca.exception.ResourceNotFoundException;


import java.util.List;
import java.util.Optional;

// Le indicamos que esta clase tiene lógica de negocio, solo sabe cómo manipular los datos
@Service
public class UsuarioService {



    private final UsuarioRepository usuarioRepository;

    // Inyectamos el repositorio de Usuario
    public UsuarioService(UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;

    }




    // Guardar o actualizar Usuario
    public Usuario saveUsuario (Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Listar todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Buscar usuario por ID
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }


    // Así devuelve excepciones
    public Usuario getUsuarioByIdOrThrow(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuario no encontrado con id " + id)
                );
    }


    // Buscar usuario por email
    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    // Eliminar usuario por ID
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Me sirve para realizar el test
    public Usuario saveFromDTO(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword()); // En un proyecto real aquí se cifraría la contraseña
        usuario.setRol(Rol.ROLE_USER);

        // Suponiendo que usas un repositorio llamado usuarioRepository
        return usuarioRepository.save(usuario);
    }

}

package com.example.biblioteca.Services;

import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

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

    // Buscar usuario por email
    public Optional<Usuario> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }


    // Eliminar usuario por ID
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }


}

package com.example.biblioteca.Services;

import com.example.biblioteca.dto.ResenaDTO;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.entity.Resena;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.ResenaRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResenaService {

    @Autowired private final ResenaRepository resenaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private LibroRepository libroRepository;

    public ResenaService (ResenaRepository resenaRepository, UsuarioRepository usuarioRepository, LibroRepository libroRepository){
        // Inyectamos el repositorio de Reseña
        this.resenaRepository = resenaRepository;
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
    }


    // Guardar o actualizar reseña
    public Resena saveResena(Resena resena) {
        return resenaRepository.save(resena);
    }

    // Listar todas las reseñas
    public List<Resena> getAllResenas() {
        return resenaRepository.findAll();
    }

    // Buscar reseña por ID
    public Optional<Resena> getResenaById(Long id) {
        return resenaRepository.findById(id);
    }

    // Listar reseñas por Usuario
    public List<Resena> getResenasByUsuario (Long usuarioId) {
        return resenaRepository.findByUsuarioId(usuarioId);
    }


    // Listar reseñas por Libro
    public List<Resena> getResenasByLibro (Long libroId) {
        return resenaRepository.findByLibroId(libroId);
    }


    // Eliminar reseña por ID
    public void deleteResena(Long id) {
        resenaRepository.deleteById(id);
    }

    // Es donde ocurre la magia de buscar al usuario y al libro en la base de datos antes de guardar.
    public Resena saveResenaDesdeDTO(ResenaDTO dto) {
        // Log para depurar en la consola de IntelliJ
        System.out.println("Guardando reseña para usuario: " + dto.getUsuarioId());

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Resena resena = new Resena();
        resena.setPuntuacion(dto.getPuntuacion());
        resena.setComentario(dto.getComentario());
        resena.setUsuario(usuario);
        resena.setLibro(libro);

        return resenaRepository.save(resena);
    }

}

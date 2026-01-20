package com.example.biblioteca.Services;


import com.example.biblioteca.dto.BibliotecaDTO;
import com.example.biblioteca.dto.BibliotecaResponseDTO;
import com.example.biblioteca.entity.Biblioteca;
import com.example.biblioteca.entity.EstadoLibro;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.BibliotecaRepository;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaService {

    private final BibliotecaRepository bibliotecaRepository;
    private final UsuarioRepository usuarioRepository;
    private final LibroRepository libroRepository;

    // Requiere 3 repositorios para poder validar que usuario y libro existen antes de unirlos
    public BibliotecaService(BibliotecaRepository bibliotecaRepository,
                             UsuarioRepository usuarioRepository,
                             LibroRepository libroRepository) {
        this.bibliotecaRepository = bibliotecaRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
    }

  /*  // Guardar o actualizar Biblioteca
    public Biblioteca saveBiblioteca (Biblioteca b) {
        return bibliotecaRepository.save(b);
    }*/

    // Listar todas las bibliotecas
    public List<Biblioteca> getAllBibliotecas() {
        return bibliotecaRepository.findAll();
    }

    // Buscar Biblioteca por ID
    public Optional<Biblioteca> getBibliotecaById(Long id) {
        return bibliotecaRepository.findById(id);
    }

    // Obtiene la colección personal de libros de un usuario específico
    // Listar bibliotecas por usuario
    public List<Biblioteca> getBibliotecasByUsuario(Long usuarioId) {
        return bibliotecaRepository.findByUsuarioId(usuarioId);
    }


    // Busca qué usuarios tienen un libro concreto en sus lista
    // Listar bibliotecas por libro
    public List<Biblioteca> getBibliotecasByLibro(Long libroId) {
        return bibliotecaRepository.findByLibroId(libroId);
    }



    // Eliminar Biblioteca por ID
    public void deleteBiblioteca(Long id) {
        bibliotecaRepository.deleteById(id);
    }

    // Convierte el DTO de entrada en Entidad, buscando las relaciones en la BD
    public Biblioteca saveFromDTO(BibliotecaDTO dto) {

        // Si el usuario o libro no existen, lanza una excepción inmediatamente
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setUsuario(usuario);
        biblioteca.setLibro(libro);
        biblioteca.setEstado(dto.getEstado());

        return bibliotecaRepository.save(biblioteca);
    }


    // Transforma la Entidad a un DTO de respuesta para ocultar datos innecesarios al cliente
    public BibliotecaResponseDTO toResponseDTO(Biblioteca b) {
        BibliotecaResponseDTO dto = new BibliotecaResponseDTO();
        dto.setId(b.getId());
        dto.setEstado(b.getEstado().name());
        dto.setUsuarioId(b.getUsuario().getId());
        dto.setLibroId(b.getLibro().getId());
        return dto;
    }


}

package com.example.biblioteca.services;

import com.example.biblioteca.Services.BibliotecaService;
import com.example.biblioteca.dto.BibliotecaDTO;
import com.example.biblioteca.entity.Biblioteca;
import com.example.biblioteca.entity.EstadoLibro;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.BibliotecaRepository;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class BibliotecaServiceTest {

    private BibliotecaRepository bibliotecaRepository;
    private UsuarioRepository usuarioRepository;
    private LibroRepository libroRepository;
    private BibliotecaService bibliotecaService;

    @BeforeEach
    void setUp() {
        bibliotecaRepository = mock(BibliotecaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        libroRepository = mock(LibroRepository.class);

        bibliotecaService = new BibliotecaService(bibliotecaRepository, usuarioRepository, libroRepository);
    }

    @Test
    void saveFromDTO_conDatosValidos_devuelveBiblioteca() {
        BibliotecaDTO dto = new BibliotecaDTO();
        dto.setEstado(EstadoLibro.LEYENDO);
        dto.setUsuarioId(1L);
        dto.setLibroId(1L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        Libro libro = new Libro();
        libro.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        Biblioteca bibliotecaGuardada = new Biblioteca();
        bibliotecaGuardada.setId(10L);
        when(bibliotecaRepository.save(any(Biblioteca.class))).thenReturn(bibliotecaGuardada);

        Biblioteca result = bibliotecaService.saveFromDTO(dto);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(bibliotecaRepository, times(1)).save(any(Biblioteca.class));
    }

    @Test
    void saveFromDTO_usuarioNoExiste_lanzaExcepcion() {
        BibliotecaDTO dto = new BibliotecaDTO();
        dto.setEstado(EstadoLibro.LEYENDO);
        dto.setUsuarioId(1L);
        dto.setLibroId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bibliotecaService.saveFromDTO(dto);
        });

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void saveFromDTO_libroNoExiste_lanzaExcepcion() {
        BibliotecaDTO dto = new BibliotecaDTO();
        dto.setEstado(EstadoLibro.LEYENDO);
        dto.setUsuarioId(1L);
        dto.setLibroId(1L);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(libroRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bibliotecaService.saveFromDTO(dto);
        });

        assertEquals("Libro no encontrado", exception.getMessage());
    }
}

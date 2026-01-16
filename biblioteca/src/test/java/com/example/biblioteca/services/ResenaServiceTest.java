package com.example.biblioteca.services;

import com.example.biblioteca.Services.ResenaService;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.entity.Resena;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import com.example.biblioteca.repository.ResenaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ResenaServiceTest {

    private ResenaRepository resenaRepository;
    private UsuarioRepository usuarioRepository;
    private LibroRepository libroRepository;
    private ResenaService resenaService;



    @BeforeEach
    void setUp() {
        // Creamos los 3 mocks necesarios
        resenaRepository = mock(ResenaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        libroRepository = mock(LibroRepository.class);

        // Pasamos los 3 al constructor del servicio
        // El orden debe ser el mismo que en tu clase ResenaService.java
        resenaService = new ResenaService(resenaRepository, usuarioRepository, libroRepository);
    }

    @Test
    void saveResena_conDatosValidos_devuelveResena() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Libro libro = new Libro();
        libro.setId(1L);

        Resena resena = new Resena();
        resena.setPuntuacion(5);
        resena.setComentario("Excelente libro");
        resena.setUsuario(usuario);
        resena.setLibro(libro);

        Resena resenaGuardada = new Resena();
        resenaGuardada.setId(10L);
        resenaGuardada.setPuntuacion(5);
        resenaGuardada.setComentario("Excelente libro");
        resenaGuardada.setUsuario(usuario);
        resenaGuardada.setLibro(libro);

        when(resenaRepository.save(any(Resena.class))).thenReturn(resenaGuardada);

        Resena result = resenaService.saveResena(resena);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(resenaRepository, times(1)).save(resena);
    }

    @Test
    void getResenaById_existe_devuelveResena() {
        Resena resena = new Resena();
        resena.setId(5L);

        when(resenaRepository.findById(5L)).thenReturn(Optional.of(resena));

        Optional<Resena> result = resenaService.getResenaById(5L);

        assertTrue(result.isPresent());
        assertEquals(5L, result.get().getId());
    }

    @Test
    void getResenaById_noExiste_devuelveEmpty() {
        when(resenaRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Resena> result = resenaService.getResenaById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getResenasByLibro_devuelveLista() {
        Libro libro = new Libro();
        libro.setId(1L);

        Resena resena1 = new Resena();
        Resena resena2 = new Resena();

        when(resenaRepository.findByLibroId(1L)).thenReturn(List.of(resena1, resena2));

        List<Resena> result = resenaService.getResenasByLibro(1L);

        assertEquals(2, result.size());
    }

    @Test
    void deleteResena_llamaARepositorio() {
        resenaService.deleteResena(3L);
        verify(resenaRepository, times(1)).deleteById(3L);
    }

}

package com.example.biblioteca.services;

import com.example.biblioteca.Services.LibroService;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.repository.LibroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LibroServiceTest {

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private LibroService libroService;


    @BeforeEach
    void setUp() {
        libroRepository = mock(LibroRepository.class);
        autorRepository = mock(AutorRepository.class); // <--- 2. CREA EL MOCK

        libroService = new LibroService(libroRepository, autorRepository);
    }

    @Test
    void saveLibro_conDatosValidos_devuelveLibro() {
        Autor autor = new Autor();
        autor.setId(1L);

        Libro libro = new Libro();
        libro.setTitulo("Fundación");
        libro.setIsbn("978-0553293357");
        libro.setAutor(autor);

        Libro libroGuardado = new Libro();
        libroGuardado.setId(10L);
        libroGuardado.setTitulo("Fundación");
        libroGuardado.setIsbn("978-0553293357");
        libroGuardado.setAutor(autor);

        when(libroRepository.save(any(Libro.class))).thenReturn(libroGuardado);

        Libro result = libroService.saveLibro(libro);

        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(libroRepository, times(1)).save(libro);
    }

    @Test
    void getLibroById_existe_devuelveLibro() {
        Libro libro = new Libro();
        libro.setId(5L);
        when(libroRepository.findById(5L)).thenReturn(Optional.of(libro));

        Optional<Libro> result = libroService.getLibroById(5L);

        assertTrue(result.isPresent());
        assertEquals(5L, result.get().getId());
    }

    @Test
    void getLibroById_noExiste_devuelveEmpty() {
        when(libroRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Libro> result = libroService.getLibroById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteLibro_llamaARepositorio() {
        libroService.deleteLibro(3L);
        verify(libroRepository, times(1)).deleteById(3L);
    }

}

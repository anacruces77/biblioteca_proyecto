package com.example.biblioteca.services;

import com.example.biblioteca.Services.AutorService;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutorServiceTest {

    private AutorRepository autorRepository;
    private AutorService autorService;

    @BeforeEach
    void setUp() {
        autorRepository = mock(AutorRepository.class);
        autorService = new AutorService(autorRepository);
    }

    @Test
    void saveAutor_conDatosValidos_devuelveAutor() {
        Autor autor = new Autor();
        autor.setNombre("J.K. Rowling");

        Autor autorGuardado = new Autor();
        autorGuardado.setId(1L);
        autorGuardado.setNombre("J.K. Rowling");

        when(autorRepository.save(any(Autor.class))).thenReturn(autorGuardado);

        Autor result = autorService.saveAutor(autor);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("J.K. Rowling", result.getNombre());
        verify(autorRepository, times(1)).save(autor);
    }

    @Test
    void getAllAutores_devuelveLista() {
        Autor a1 = new Autor();
        Autor a2 = new Autor();

        when(autorRepository.findAll()).thenReturn(List.of(a1, a2));

        List<Autor> result = autorService.getAllAutores();

        assertEquals(2, result.size());
    }

    @Test
    void getAutorById_existe_devuelveAutor() {
        Autor a = new Autor();
        a.setId(5L);

        when(autorRepository.findById(5L)).thenReturn(Optional.of(a));

        Optional<Autor> result = autorService.getAutorById(5L);

        assertTrue(result.isPresent());
        assertEquals(5L, result.get().getId());
    }

    @Test
    void getAutorById_noExiste_devuelveEmpty() {
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Autor> result = autorService.getAutorById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteAutor_llamaARepositorio() {
        autorService.deleteAutor(3L);
        verify(autorRepository, times(1)).deleteById(3L);
    }
}

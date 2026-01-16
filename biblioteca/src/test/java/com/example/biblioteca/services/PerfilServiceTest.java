package com.example.biblioteca.services;

import com.example.biblioteca.Services.PerfilService;
import com.example.biblioteca.entity.Perfil;
import com.example.biblioteca.repository.PerfilRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PerfilServiceTest {

    private PerfilRepository perfilRepository;
    private PerfilService perfilService;

    @BeforeEach
    void setUp() {
        perfilRepository = mock(PerfilRepository.class);
        perfilService = new PerfilService(perfilRepository);
    }

    @Test
    void savePerfil_conDatosValidos_devuelvePerfil() {
        Perfil perfil = new Perfil();
        perfil.setNickname("AnaCool");
        perfil.setAvatar("ana.png");

        Perfil perfilGuardado = new Perfil();
        perfilGuardado.setId(1L);
        perfilGuardado.setNickname("AnaCool");
        perfilGuardado.setAvatar("ana.png");

        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfilGuardado);

        Perfil result = perfilService.savePerfil(perfil);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("AnaCool", result.getNickname());
        verify(perfilRepository, times(1)).save(perfil);
    }

    @Test
    void getAllPerfiles_devuelveLista() {
        Perfil p1 = new Perfil();
        Perfil p2 = new Perfil();

        when(perfilRepository.findAll()).thenReturn(List.of(p1, p2));

        List<Perfil> result = perfilService.getAllPerfiles();

        assertEquals(2, result.size());
    }

    @Test
    void getPerfilById_existe_devuelvePerfil() {
        Perfil p = new Perfil();
        p.setId(5L);

        when(perfilRepository.findById(5L)).thenReturn(Optional.of(p));

        Optional<Perfil> result = perfilService.getPerfilById(5L);

        assertTrue(result.isPresent());
        assertEquals(5L, result.get().getId());
    }

    @Test
    void getPerfilById_noExiste_devuelveEmpty() {
        when(perfilRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Perfil> result = perfilService.getPerfilById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void deletePerfil_llamaARepositorio() {
        perfilService.deletePerfil(3L);
        verify(perfilRepository, times(1)).deleteById(3L);
    }
}

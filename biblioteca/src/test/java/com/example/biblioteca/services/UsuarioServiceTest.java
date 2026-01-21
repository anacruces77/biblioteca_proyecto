package com.example.biblioteca.services;

import com.example.biblioteca.Services.UsuarioService;
import com.example.biblioteca.entity.Rol;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;



    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void saveUsuario_conDatosValidos_devuelveUsuario() {
        Usuario usuario = new Usuario(); // Usuario que quiero guardar, sin ID aun
        usuario.setNombre("Ana");
        usuario.setEmail("ana@mail.com");
        usuario.setPassword("123456");
        usuario.setRol(Rol.USER);

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);  // Simula haber pasado por la base de datos
        usuarioGuardado.setNombre("Ana");
        usuarioGuardado.setEmail("ana@mail.com");
        usuarioGuardado.setPassword("123456");
        usuario.setRol(Rol.USER);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioGuardado);

        Usuario result = usuarioService.saveUsuario(usuario);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ana", result.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void getAllUsuarios_devuelveLista() {
        Usuario u1 = new Usuario();
        Usuario u2 = new Usuario();

        when(usuarioRepository.findAll()).thenReturn(List.of(u1, u2));

        List<Usuario> result = usuarioService.getAllUsuarios();

        assertEquals(2, result.size());
    }

    @Test
    void getUsuarioById_existe_devuelveUsuario() {
        Usuario u = new Usuario();
        u.setId(5L);

        when(usuarioRepository.findById(5L)).thenReturn(Optional.of(u));

        Optional<Usuario> result = usuarioService.getUsuarioById(5L);

        assertTrue(result.isPresent());
        assertEquals(5L, result.get().getId());
    }

    @Test
    void getUsuarioById_noExiste_devuelveEmpty() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.getUsuarioById(99L);

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteUsuario_llamaARepositorio() {
        usuarioService.deleteUsuario(3L);
        verify(usuarioRepository, times(1)).deleteById(3L);
    }
}

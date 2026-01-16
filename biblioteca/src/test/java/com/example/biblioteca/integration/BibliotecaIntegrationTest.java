package com.example.biblioteca.integration;

import com.example.biblioteca.dto.BibliotecaDTO;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.EstadoLibro;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.BibliotecaRepository;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

// Importante: asegúrate de tener este import para el CSRF por si acaso
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) // Fuerza el modo Mock
@AutoConfigureMockMvc(addFilters = false) // Desactiva filtros de seguridad para simplificar
@Transactional // Limpia la base de datos automáticamente después de cada test
@ActiveProfiles("test")
class BibliotecaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;


    private Usuario usuario;
    private Libro libro;

    @BeforeEach
    void setUp() {
        // 1. Limpieza en orden (de más dependiente a menos)
        bibliotecaRepository.deleteAll();
        libroRepository.deleteAll();
        usuarioRepository.deleteAll();
        autorRepository.deleteAll();


        Autor autor = new Autor();
        autor.setNombre("Autor de Prueba");
        autor = autorRepository.save(autor);


        usuario = new Usuario();
        usuario.setNombre("TestUser");
        usuario.setEmail("test" + System.currentTimeMillis() + "@mail.com");
        usuario.setPassword("123456");
        usuario = usuarioRepository.save(usuario);


        libro = new Libro();
        libro.setTitulo("Test Libro");
        libro.setIsbn("ISBN-" + System.currentTimeMillis());
        libro.setAnioPublicacion(2026);
        libro.setAutor(autor);
        libro = libroRepository.save(libro);
    }

    @Test
    @WithMockUser(roles = "ADMIN") // Le damos rol de ADMIN para evitar cualquier restricción
    void crearBiblioteca_yVerificarExistencia() throws Exception {
        BibliotecaDTO dto = new BibliotecaDTO();
        dto.setEstado(EstadoLibro.LEYENDO);
        dto.setUsuarioId(usuario.getId());
        dto.setLibroId(libro.getId());

        mockMvc.perform(post("/api/bibliotecas")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("LEYENDO"));

        assertEquals(1, bibliotecaRepository.count());
    }

    @Test
    @WithMockUser
    void getAllBibliotecas_devuelveLista() throws Exception {
        com.example.biblioteca.entity.Biblioteca b = new com.example.biblioteca.entity.Biblioteca();
        b.setEstado(EstadoLibro.LEYENDO);
        b.setUsuario(usuario);
        b.setLibro(libro);
        bibliotecaRepository.save(b);

        mockMvc.perform(get("/api/bibliotecas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].estado").value("LEYENDO"));
    }
}
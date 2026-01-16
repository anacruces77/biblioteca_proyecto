package com.example.biblioteca.integration;

import com.example.biblioteca.dto.ResenaDTO;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.ResenaRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@ActiveProfiles("test")
public class ResenaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository; // Necesario para crear el libro correctamente

    @BeforeEach
    void setUp() {
        // Soluciona el error 404 registrando correctamente los controladores
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Limpieza en orden de integridad referencial
        resenaRepository.deleteAll();
        libroRepository.deleteAll();
        usuarioRepository.deleteAll();
        autorRepository.deleteAll();
    }

    @Test
    void crearResena_valida_devuelve201() throws Exception {
        // 1. El libro necesita un Autor (Sin esto daba error de integridad)
        Autor autor = new Autor();
        autor.setNombre("Autor Test");
        autor = autorRepository.save(autor);

        // 2. Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Usuario Test");
        usuario.setEmail("usuario" + System.currentTimeMillis() + "@test.com");
        usuario.setPassword("123456");
        usuario = usuarioRepository.save(usuario);

        // 3. Crear Libro con su Autor
        Libro libro = new Libro();
        libro.setTitulo("Libro Test");
        libro.setIsbn("111-2223334445");
        libro.setAnioPublicacion(2026);
        libro.setAutor(autor);
        libro = libroRepository.save(libro);

        // 4. Preparar DTO (Lo que espera el Controller)
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(5);
        dto.setComentario("Excelente libro");
        dto.setUsuarioId(usuario.getId());
        dto.setLibroId(libro.getId());

        // 5. Ejecutar Test
        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllResenas_devuelve200() throws Exception {
        mockMvc.perform(get("/api/resenas"))
                .andExpect(status().isOk());
    }
}
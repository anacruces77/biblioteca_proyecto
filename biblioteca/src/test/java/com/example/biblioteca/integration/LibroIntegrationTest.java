package com.example.biblioteca.integration;

import com.example.biblioteca.dto.LibroDTO;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.LibroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach; // Importante
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders; // Para el setup manual
import org.springframework.web.context.WebApplicationContext; // Para el setup manual
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@ActiveProfiles("test")
public class LibroIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext; // Clave para evitar el 404

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @BeforeEach
    void setUp() {
        // Esto fuerza a MockMvc a cargar tu LibroController correctamente
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // Limpiamos para que cada test sea independiente
        libroRepository.deleteAll();
        autorRepository.deleteAll();
    }

    @Test
    void crearLibro_valido_devuelve201() throws Exception {
        Autor autor = new Autor();
        autor.setNombre("Autor Test");
        autor = autorRepository.save(autor);

        LibroDTO dto = new LibroDTO();
        dto.setTitulo("Libro Test");
        dto.setIsbn("123-4567890123");
        dto.setAnioPublicacion(2026);
        dto.setAutorId(autor.getId());

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Libro Test"));
    }

    @Test
    void getAllLibros_devuelve200() throws Exception {
        // 1. Insertamos un libro manualmente para que la lista no esté vacía
        Autor autor = new Autor();
        autor.setNombre("Autor para GET");
        autor = autorRepository.save(autor);

        Libro libro = new Libro();
        libro.setTitulo("Libro Existente");
        libro.setIsbn("111-2223334445");
        libro.setAnioPublicacion(2024);
        libro.setAutor(autor);
        libroRepository.save(libro);

        // 2. Ahora probamos el GET
        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].titulo").value("Libro Existente"));
    }

    @Test
    void getLibroById_noExiste_devuelve404() throws Exception {
        mockMvc.perform(get("/api/libros/9999"))
                .andExpect(status().isNotFound());
    }
}
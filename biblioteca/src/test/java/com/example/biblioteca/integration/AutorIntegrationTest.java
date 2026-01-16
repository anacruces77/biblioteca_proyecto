package com.example.biblioteca.integration;

import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.repository.AutorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AutorIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AutorRepository autorRepository;

    @Test
    void crearAutor_valido_devuelve201() throws Exception {
        Autor autor = new Autor();
        autor.setNombre("Autor Test");

        mockMvc.perform(post("/api/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(autor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Autor Test"));
    }

    @Test
    void getAllAutores_devuelve200() throws Exception {
        mockMvc.perform(get("/api/autores"))
                .andExpect(status().isOk());
    }

    @Test
    void getAutorById_noExiste_devuelve404() throws Exception {
        mockMvc.perform(get("/api/autores/9999"))
                .andExpect(status().isNotFound());
    }
}

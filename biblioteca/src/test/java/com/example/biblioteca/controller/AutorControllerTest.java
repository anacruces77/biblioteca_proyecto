package com.example.biblioteca.controller;
import com.example.biblioteca.Services.AutorService;
import com.example.biblioteca.entity.Autor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(AutorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AutorService autorService;

    @Autowired
    private ObjectMapper objectMapper;




    // GET /api/autores
    @Test
    void getAllAutores_devuelve200() throws Exception {
        when(autorService.getAllAutores())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/autores"))
                .andExpect(status().isOk());
    }



    // GET /api/autores/{id}
    @Test
    void getAutorById_existe_devuelve200() throws Exception {
        Autor autor = new Autor();
        autor.setId(1L);
        autor.setNombre("J.R.R. Tolkien");

        when(autorService.getAutorById(1L))
                .thenReturn(Optional.of(autor));

        mockMvc.perform(get("/api/autores/1"))
                .andExpect(status().isOk());
    }


    @Test
    void getAutorById_noExiste_devuelve404() throws Exception {
        when(autorService.getAutorById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/autores/99"))
                .andExpect(status().isNotFound());
    }



    // POST /api/autores
    @Test
    void crearAutor_conDatosValidos_devuelve201() throws Exception {
        Autor autor = new Autor();
        autor.setNombre("Isaac Asimov");

        Autor autorGuardado = new Autor();
        autorGuardado.setId(10L);
        autorGuardado.setNombre("Isaac Asimov");

        when(autorService.saveAutor(any(Autor.class)))
                .thenReturn(autorGuardado);

        mockMvc.perform(post("/api/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(autor)))
                .andExpect(status().isCreated());
    }



    // DELETE /api/autores/{id}
    @Test
    void deleteAutor_devuelve204() throws Exception {
        mockMvc.perform(delete("/api/autores/1"))
                .andExpect(status().isNoContent());
    }

}

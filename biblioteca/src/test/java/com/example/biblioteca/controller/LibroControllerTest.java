package com.example.biblioteca.controller;

import com.example.biblioteca.Services.LibroService;
import com.example.biblioteca.dto.LibroDTO;
import com.example.biblioteca.entity.Libro;
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



@WebMvcTest(com.example.biblioteca.controller.LibroController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LibroService libroService;

    @Autowired
    private ObjectMapper objectMapper;




    // GET /api/libros
    @Test
    void getAllLibros_devuelve200() throws Exception {
        when(libroService.getAllLibros()).thenReturn(List.of());

        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk());
    }


    // GET /api/libros/{id}
    @Test
    void getLibroById_existe_devuelve200() throws Exception {
        Libro libro = new Libro();
        libro.setId(1L);
        libro.setTitulo("El Señor de los Anillos");

        when(libroService.getLibroById(1L)).thenReturn(Optional.of(libro));

        mockMvc.perform(get("/api/libros/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getLibroById_noExiste_devuelve404() throws Exception {
        when(libroService.getLibroById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/libros/99"))
                .andExpect(status().isNotFound());
    }


    // POST /api/libros
    @Test
    void crearLibro_conDatosValidos_devuelve201() throws Exception {
        LibroDTO dto = new LibroDTO();
        dto.setTitulo("Fundación");
        dto.setIsbn("978-0553293357");
        dto.setAnioPublicacion(1951);
        dto.setAutorId(3L);

        Libro libroGuardado = new Libro();
        libroGuardado.setId(10L);
        libroGuardado.setTitulo("Fundación");

        when(libroService.saveFromDTO(any(LibroDTO.class))).thenReturn(libroGuardado);

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }


    // DELETE /api/libros/{id}
    @Test
    void deleteLibro_devuelve204() throws Exception {
        mockMvc.perform(delete("/api/libros/1"))
                .andExpect(status().isNoContent());
    }


}

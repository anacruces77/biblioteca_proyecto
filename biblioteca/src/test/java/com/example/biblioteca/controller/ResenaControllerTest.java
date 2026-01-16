package com.example.biblioteca.controller;

import com.example.biblioteca.Services.ResenaService;
import com.example.biblioteca.dto.ResenaDTO;
import com.example.biblioteca.entity.Resena;
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


@WebMvcTest(ResenaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenaService resenaService;

    @Autowired
    private ObjectMapper objectMapper;


    // POST /api/resenas
    @Test
    void crearResena_conDatosInvalidos_devuelve400() throws Exception {
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(null);   // inválido
        dto.setComentario("");     // inválido
        dto.setUsuarioId(null);    // inválido
        dto.setLibroId(null);      // inválido

        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearResena_conDatosValidos_devuelve201() throws Exception {
        ResenaDTO dto = new ResenaDTO();
        dto.setPuntuacion(5);
        dto.setComentario("Muy buen libro");
        dto.setUsuarioId(1L);
        dto.setLibroId(1L);

        Resena resenaGuardada = new Resena();
        resenaGuardada.setId(10L);

        when(resenaService.saveResena(any(Resena.class)))
                .thenReturn(resenaGuardada);

        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }


    // GET /api/resenas
    @Test
    void getAllResenas_devuelve200() throws Exception {
        when(resenaService.getAllResenas())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/resenas"))
                .andExpect(status().isOk());
    }


    // GET /api/resenas/{id}
    @Test
    void getResenaById_noExiste_devuelve404() throws Exception {
        when(resenaService.getResenaById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/resenas/99"))
                .andExpect(status().isNotFound());
    }

}

package com.example.biblioteca.controller;

import com.example.biblioteca.Services.PerfilService;
import com.example.biblioteca.dto.PerfilDTO;
import com.example.biblioteca.dto.PerfilResponseDTO;
import com.example.biblioteca.entity.Perfil;
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


@WebMvcTest(com.example.biblioteca.controller.PerfilController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PerfilControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PerfilService perfilService;

    @Autowired
    private ObjectMapper objectMapper;




    // GET /api/perfiles
    @Test
    void getAllPerfiles_devuelve200() throws Exception {
        when(perfilService.getAllPerfiles()).thenReturn(List.of());

        mockMvc.perform(get("/api/perfiles"))
                .andExpect(status().isOk());
    }


    // GET /api/perfiles/{id}
    @Test
    void getPerfilById_existe_devuelve200() throws Exception {
        Perfil perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNickname("AnaCool");

        when(perfilService.getPerfilById(1L)).thenReturn(Optional.of(perfil));

        mockMvc.perform(get("/api/perfiles/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getPerfilById_noExiste_devuelve404() throws Exception {
        when(perfilService.getPerfilById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/perfiles/99"))
                .andExpect(status().isNotFound());
    }


    // POST /api/perfiles
    @Test
    void crearPerfil_conDatosValidos_devuelve201() throws Exception {
        PerfilDTO dto = new PerfilDTO();
        dto.setNickname("CarlosDev");
        dto.setAvatar("carlos.png");
        dto.setUsuarioId(2L);

        Perfil perfilGuardado = new Perfil();
        perfilGuardado.setId(10L);
        perfilGuardado.setNickname("CarlosDev");

        PerfilResponseDTO responseDTO = new PerfilResponseDTO();
        responseDTO.setId(10L);
        responseDTO.setNickname("CarlosDev");

        when(perfilService.saveFromDTO(any(PerfilDTO.class))).thenReturn(perfilGuardado);
        when(perfilService.toResponseDTO(any(Perfil.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }


    @Test
    void crearPerfil_conDatosInvalidos_devuelve400() throws Exception {
        PerfilDTO dto = new PerfilDTO(); // campos nulos

        mockMvc.perform(post("/api/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }


    // DELETE /api/perfiles/{id}
    @Test
    void deletePerfil_devuelve204() throws Exception {
        mockMvc.perform(delete("/api/perfiles/1"))
                .andExpect(status().isNoContent());
    }

}

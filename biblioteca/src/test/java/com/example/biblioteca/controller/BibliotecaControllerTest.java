package com.example.biblioteca.controller;

import com.example.biblioteca.Services.BibliotecaService;
import com.example.biblioteca.dto.BibliotecaDTO;
import com.example.biblioteca.dto.BibliotecaResponseDTO;
import com.example.biblioteca.entity.Biblioteca;
import com.example.biblioteca.entity.EstadoLibro;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


// Cada controlador REST dispone de su propia clase de pruebas usando MockMvc,
// donde se validan los distintos escenarios posibles de cada endpoint
// (casos válidos, inválidos y errores HTTP
@WebMvcTest(BibliotecaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BibliotecaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BibliotecaService bibliotecaService;

    @Autowired
    private ObjectMapper objectMapper;




    @Test
    void crearBiblioteca_conDatosInvalidos_devuelve400() throws Exception {
        BibliotecaDTO dto = new BibliotecaDTO();
        dto.setEstado(null);        // inválido
        dto.setUsuarioId(null);   // inválido
        dto.setLibroId(null);     // inválido

        mockMvc.perform(post("/api/bibliotecas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crearBiblioteca_conDatosValidos_devuelve201() throws Exception {
        // 1. Preparamos los datos de entrada (DTO)
        BibliotecaDTO dtoEntrada = new BibliotecaDTO();
        dtoEntrada.setEstado(EstadoLibro.LEYENDO);
        dtoEntrada.setUsuarioId(1L);
        dtoEntrada.setLibroId(1L);

        // 2. Preparamos lo que el servicio va a devolver (Simulación/Mock)
        Biblioteca bibliotecaGuardada = new Biblioteca();
        bibliotecaGuardada.setId(10L); // Le asignamos un ID ficticio

        BibliotecaResponseDTO responseDTO = new BibliotecaResponseDTO();
        responseDTO.setId(10L);
        responseDTO.setEstado("LEYENDO");

        // 3. Configuramos el comportamiento del Mock
        // "Cuando el servicio guarde cualquier DTO, devuelve el objeto bibliotecaGuardada"
        when(bibliotecaService.saveFromDTO(any(BibliotecaDTO.class))).thenReturn(bibliotecaGuardada);
        // "Cuando el servicio convierta esa entidad a DTO, devuelve el responseDTO"
        when(bibliotecaService.toResponseDTO(any(Biblioteca.class))).thenReturn(responseDTO);

        // 4. Ejecutamos la petición POST
        mockMvc.perform(post("/api/bibliotecas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoEntrada)))
                // 5. Verificamos que devuelva 201 Created
                .andExpect(status().isCreated());
    }



    @Test
    void getAllBiblioteca_devuelve200() throws Exception {
        when(bibliotecaService.getAllBibliotecas())
                .thenReturn(List.of());

        mockMvc.perform(get("/api/bibliotecas"))
                .andExpect(status().isOk());
    }

    @Test
    void getBibliotecaById_noExiste_devuelve404() throws Exception {
        when(bibliotecaService.getBibliotecaById(99L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bibliotecas/99"))
                .andExpect(status().isNotFound());
    }


}

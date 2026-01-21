package com.example.biblioteca.controller;

import com.example.biblioteca.Services.UsuarioService;
import com.example.biblioteca.dto.UsuarioDTO;
import com.example.biblioteca.entity.Rol;
import com.example.biblioteca.entity.Usuario;
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



@WebMvcTest(com.example.biblioteca.controller.UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;



    // GET /api/usuarios
    @Test
    void getAllUsuarios_devuelve200() throws Exception {
        when(usuarioService.getAllUsuarios()).thenReturn(List.of());

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
    }


    // GET /api/usuarios/{id}
    @Test
    void getUsuarioById_existe_devuelve200() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Ana");
        usuario.setEmail("ana@mail.com");
        usuario.setRol(Rol.USER);

        when(usuarioService.getUsuarioById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk());
    }



    @Test
    void getUsuarioById_noExiste_devuelve404() throws Exception {
        when(usuarioService.getUsuarioById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }



    // POST /api/usuarios
    @Test
    void crearUsuario_conDatosValidos_devuelve201() throws Exception {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombre("Carlos");
        dto.setEmail("carlos@mail.com");
        dto.setPassword("123456");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(10L);
        usuarioGuardado.setNombre("Carlos");

        when(usuarioService.saveFromDTO(any(UsuarioDTO.class))).thenReturn(usuarioGuardado);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }




    @Test
    void crearUsuario_conDatosInvalidos_devuelve400() throws Exception {
        UsuarioDTO dto = new UsuarioDTO(); // campos nulos

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }




    // DELETE /api/usuarios/{id}
    @Test
    void deleteUsuario_devuelve204() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }


}

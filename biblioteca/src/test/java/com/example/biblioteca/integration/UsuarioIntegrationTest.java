package com.example.biblioteca.integration;

import com.example.biblioteca.controller.UsuarioController;
import com.example.biblioteca.entity.Rol;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.UsuarioRepository;
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
public class UsuarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void crearUsuario_valido_devuelve201() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNombre("TestUser");
        usuario.setEmail("testuser@mail.com");
        usuario.setPassword("123456");
        usuario.setRol(Rol.USER);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("TestUser"))
                .andExpect(jsonPath("$.email").value("testuser@mail.com"))
                .andExpect(jsonPath("$.rol").value("ROLE_USER"));
    }

    @Test
    void getUsuarios_devuelve200() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
    }
}

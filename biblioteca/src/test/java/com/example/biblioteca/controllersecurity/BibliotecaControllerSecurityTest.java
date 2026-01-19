package com.example.biblioteca.controllersecurity;


import com.example.biblioteca.entity.Biblioteca;
import com.example.biblioteca.entity.Rol;
import com.example.biblioteca.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BibliotecaControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postBiblioteca_conRolAdmin_devuelve201() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_ADMIN));

        Biblioteca b = new Biblioteca();
        b.setUsuario(null); // simplificado

        mockMvc.perform(post("/api/bibliotecas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(b))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void postBiblioteca_conRolUser_devuelve403() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_USER));

        Biblioteca b = new Biblioteca();
        b.setUsuario(null);

        mockMvc.perform(post("/api/bibliotecas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(b))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBiblioteca_conRolAdmin_devuelve204() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_ADMIN));
        mockMvc.perform(delete("/api/bibliotecas/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBiblioteca_conRolUser_devuelve403() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_USER));
        mockMvc.perform(delete("/api/bibliotecas/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBiblioteca_sinToken_devuelve401() throws Exception {
        mockMvc.perform(delete("/api/bibliotecas/1"))
                .andExpect(status().isUnauthorized());
    }

    private com.example.biblioteca.entity.Usuario createUser(Rol rol) {
        com.example.biblioteca.entity.Usuario u = new com.example.biblioteca.entity.Usuario();
        u.setRol(rol);
        return u;
    }
}
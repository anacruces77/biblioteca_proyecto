package com.example.biblioteca.integration;

import com.example.biblioteca.entity.Perfil;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.repository.PerfilRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PerfilIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    @Transactional
    void crearPerfil_valido_devuelve201() throws Exception {

        Usuario u = new Usuario();
        u.setNombre("PerfilUser");
        u.setEmail("perfil" + System.currentTimeMillis() + "@mail.com"); // Email único
        u.setPassword("123456");
        u = usuarioRepository.save(u);


        Map<String, Object> perfilDTO = new HashMap<>();
        perfilDTO.put("nickname", "PerfilNick");
        perfilDTO.put("avatar", "avatar.png");
        perfilDTO.put("usuarioId", u.getId()); // Enviamos solo el ID

        mockMvc.perform(post("/api/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(perfilDTO))) // Enviamos el mapa/DTO
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nickname").value("PerfilNick"));
    }

    @Test
    void getPerfiles_devuelve200() throws Exception {
        mockMvc.perform(get("/api/perfiles"))
                .andExpect(status().isOk());
    }
}

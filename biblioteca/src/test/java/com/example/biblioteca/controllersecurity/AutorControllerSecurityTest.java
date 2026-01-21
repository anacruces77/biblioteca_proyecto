package com.example.biblioteca.controllersecurity;


import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Rol;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
public class AutorControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private com.example.biblioteca.repository.UsuarioRepository usuarioRepository;

    @BeforeEach
    void setup() {
        usuarioRepository.deleteAll(); // Limpia la base de datos antes de cada test
    }

    @Test
    void postAutor_conRolAdmin_devuelve201() throws Exception {
        Usuario admin = saveUserInDB(Rol.ADMIN, "admin_post@test.com");
        String token = jwtUtil.generateToken(admin);

        Autor nuevo = new Autor();
        nuevo.setNombre("AutorTest");

        mockMvc.perform(post("/api/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void postAutor_conRolUser_devuelve403() throws Exception {
        Usuario user = saveUserInDB(Rol.USER, "user_post@test.com");
        String token = jwtUtil.generateToken(user);

        Autor nuevo = new Autor();
        nuevo.setNombre("AutorTest");

        mockMvc.perform(post("/api/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteAutor_conRolAdmin_devuelve204() throws Exception {
        // Usamos saveUserInDB
        Usuario admin = saveUserInDB(Rol.ADMIN, "admin_del@test.com");
        String token = jwtUtil.generateToken(admin);

        mockMvc.perform(delete("/api/autores/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAutor_conRolUser_devuelve403() throws Exception {
        // CUsamos saveUserInDB
        Usuario user = saveUserInDB(Rol.USER, "user_del@test.com");
        String token = jwtUtil.generateToken(user);

        mockMvc.perform(delete("/api/autores/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteAutor_sinToken_devuelve401() throws Exception {
        mockMvc.perform(delete("/api/autores/1"))
                .andExpect(status().isUnauthorized());
    }

    // Método único para guardar usuarios reales en la BD de pruebas
    private Usuario saveUserInDB(Rol rol, String email) {
        Usuario u = new Usuario();
        u.setEmail(email);
        u.setNombre("Test User");
        u.setPassword("password123");
        u.setRol(rol);
        return usuarioRepository.save(u);
    }
}
package com.example.biblioteca.controllersecurity;



import com.example.biblioteca.entity.Perfil;
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
public class PerfilControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void postPerfil_conRolAdmin_devuelve201() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_ADMIN));

        Perfil p = new Perfil();
        p.setNickname("NickTest");

        mockMvc.perform(post("/api/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void postPerfil_conRolUser_devuelve403() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_USER));

        Perfil p = new Perfil();
        p.setNickname("NickTest");

        mockMvc.perform(post("/api/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(p))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deletePerfil_conRolAdmin_devuelve204() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_ADMIN));
        mockMvc.perform(delete("/api/perfiles/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePerfil_conRolUser_devuelve403() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_USER));
        mockMvc.perform(delete("/api/perfiles/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deletePerfil_sinToken_devuelve401() throws Exception {
        mockMvc.perform(delete("/api/perfiles/1"))
                .andExpect(status().isUnauthorized());
    }

    private com.example.biblioteca.entity.Usuario createUser(Rol rol) {
        com.example.biblioteca.entity.Usuario u = new com.example.biblioteca.entity.Usuario();
        u.setRol(rol);
        return u;
    }
}
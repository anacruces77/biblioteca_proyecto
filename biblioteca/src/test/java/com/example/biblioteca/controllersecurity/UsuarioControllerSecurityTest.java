package com.example.biblioteca.controllersecurity;

import com.example.biblioteca.entity.Rol;
import com.example.biblioteca.entity.Usuario;
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
public class UsuarioControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deleteUsuario_conRolAdmin_devuelve204() throws Exception {
        Usuario admin = new Usuario();
        admin.setId(1L);
        admin.setRol(Rol.ROLE_ADMIN);

        String token = jwtUtil.generateToken(admin);

        mockMvc.perform(delete("/api/usuarios/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUsuario_conRolUser_devuelve403() throws Exception {
        Usuario user = new Usuario();
        user.setId(2L);
        user.setRol(Rol.ROLE_USER);

        String token = jwtUtil.generateToken(user);

        mockMvc.perform(delete("/api/usuarios/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUsuario_sinToken_devuelve401() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void postUsuario_conRolAdmin_devuelve201() throws Exception {
        Usuario admin = new Usuario();
        admin.setRol(Rol.ROLE_ADMIN);
        String token = jwtUtil.generateToken(admin);

        Usuario nuevo = new Usuario();
        nuevo.setNombre("NuevoUser");
        nuevo.setEmail("nuevo@mail.com");
        nuevo.setPassword("123456");

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void postUsuario_conRolUser_devuelve403() throws Exception {
        Usuario user = new Usuario();
        user.setRol(Rol.ROLE_USER);
        String token = jwtUtil.generateToken(user);

        Usuario nuevo = new Usuario();
        nuevo.setNombre("NuevoUser");
        nuevo.setEmail("nuevo@mail.com");
        nuevo.setPassword("123456");

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void putUsuario_conRolAdmin_devuelve200() throws Exception {
        Usuario admin = new Usuario();
        admin.setRol(Rol.ROLE_ADMIN);
        String token = jwtUtil.generateToken(admin);

        Usuario edit = new Usuario();
        edit.setNombre("EditUser");
        edit.setEmail("edit@mail.com");

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(edit))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void putUsuario_conRolUser_devuelve403() throws Exception {
        Usuario user = new Usuario();
        user.setRol(Rol.ROLE_USER);
        String token = jwtUtil.generateToken(user);

        Usuario edit = new Usuario();
        edit.setNombre("EditUser");
        edit.setEmail("edit@mail.com");

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(edit))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void putUsuario_sinToken_devuelve401() throws Exception {
        Usuario edit = new Usuario();
        edit.setNombre("EditUser");
        edit.setEmail("edit@mail.com");

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(edit)))
                .andExpect(status().isForbidden());
    }
}
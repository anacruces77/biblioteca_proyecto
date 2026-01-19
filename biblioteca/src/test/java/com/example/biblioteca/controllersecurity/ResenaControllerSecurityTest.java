package com.example.biblioteca.controllersecurity;


import com.example.biblioteca.entity.Resena;
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
public class ResenaControllerSecurityTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private com.example.biblioteca.repository.UsuarioRepository usuarioRepository;
    @Autowired private com.example.biblioteca.repository.LibroRepository libroRepository;

    private com.example.biblioteca.entity.Libro libroTest;

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        usuarioRepository.deleteAll();
        libroRepository.deleteAll();
        // Creamos un libro base para que los POST tengan un ID válido que buscar
        com.example.biblioteca.entity.Libro l = new com.example.biblioteca.entity.Libro();
        l.setTitulo("Libro de Prueba");
        libroTest = libroRepository.save(l);
    }

    @Test
    void postResena_conRolAdmin_devuelve201() throws Exception {
        com.example.biblioteca.entity.Usuario admin = saveUserInDB(Rol.ROLE_ADMIN, "admin@test.com");
        String token = jwtUtil.generateToken(admin);

        com.example.biblioteca.dto.ResenaDTO dto = new com.example.biblioteca.dto.ResenaDTO();
        dto.setPuntuacion(5);
        dto.setComentario("Excelente");
        dto.setUsuarioId(admin.getId());
        dto.setLibroId(libroTest.getId()); // ID real guardado en setup()

        mockMvc.perform(post("/api/resenas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteResena_conRolAdmin_devuelve204() throws Exception {
        com.example.biblioteca.entity.Usuario admin = saveUserInDB(Rol.ROLE_ADMIN, "del_admin@test.com");
        String token = jwtUtil.generateToken(admin);

        mockMvc.perform(delete("/api/resenas/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteResena_conRolUser_devuelve403() throws Exception {
        com.example.biblioteca.entity.Usuario user = saveUserInDB(Rol.ROLE_USER, "user@test.com");
        String token = jwtUtil.generateToken(user);

        mockMvc.perform(delete("/api/resenas/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    // ... mantener el test sin token (401) ...

    private com.example.biblioteca.entity.Usuario saveUserInDB(Rol rol, String email) {
        com.example.biblioteca.entity.Usuario u = new com.example.biblioteca.entity.Usuario();
        u.setEmail(email);
        u.setNombre("Test");
        u.setPassword("1234");
        u.setRol(rol);
        return usuarioRepository.save(u);
    }

}
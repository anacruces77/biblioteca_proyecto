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

    @Autowired private MockMvc mockMvc;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private com.example.biblioteca.repository.UsuarioRepository usuarioRepository;

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        usuarioRepository.deleteAll(); // Limpia la tabla para que no haya correos repetidos
    }

    @Test
    void postBiblioteca_conRolAdmin_devuelve201() throws Exception {
        com.example.biblioteca.entity.Usuario admin = saveUserInDB(Rol.ADMIN, "admin_post@test.com");
        String token = jwtUtil.generateToken(admin);

        com.example.biblioteca.dto.BibliotecaDTO dto = new com.example.biblioteca.dto.BibliotecaDTO();
        dto.setUsuarioId(admin.getId());
        dto.setLibroId(1L);
        // Usa el nombre exacto de tu Enum (por ejemplo, EstadoLibro.LEYENDO)
        dto.setEstado(com.example.biblioteca.entity.EstadoLibro.LEYENDO);

        mockMvc.perform(post("/api/bibliotecas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void postBiblioteca_conRolUser_devuelve403() throws Exception {
        // 1. Guardamos el usuario con rol USER
        com.example.biblioteca.entity.Usuario user = saveUserInDB(Rol.USER, "user_post@test.com");
        String token = jwtUtil.generateToken(user);

        // 2. Creamos el DTO con DATOS VÁLIDOS (incluyendo el Enum)
        com.example.biblioteca.dto.BibliotecaDTO dto = new com.example.biblioteca.dto.BibliotecaDTO();
        dto.setUsuarioId(user.getId());
        dto.setLibroId(1L);
        // IMPORTANTE: Debes poner el estado para que no de error 400
        dto.setEstado(com.example.biblioteca.entity.EstadoLibro.LEYENDO);

        // 3. Realizamos la petición
        mockMvc.perform(post("/api/bibliotecas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer " + token))
                // 4. Ahora sí, como los datos son válidos, Spring llegará a comprobar
                // la seguridad y devolverá 403 porque no es ADMIN.
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBiblioteca_conRolAdmin_devuelve204() throws Exception {
        com.example.biblioteca.entity.Usuario admin = saveUserInDB(Rol.ADMIN, "admin_del@test.com");
        String token = jwtUtil.generateToken(admin);

        mockMvc.perform(delete("/api/bibliotecas/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBiblioteca_conRolUser_devuelve403() throws Exception {
        com.example.biblioteca.entity.Usuario user = saveUserInDB(Rol.USER, "user_del@test.com");
        String token = jwtUtil.generateToken(user);

        mockMvc.perform(delete("/api/bibliotecas/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBiblioteca_sinToken_devuelve401() throws Exception {
        mockMvc.perform(delete("/api/bibliotecas/1"))
                .andExpect(status().isUnauthorized());
    }

    // ELIMINA EL MÉTODO createUser y usa solo este:
    private com.example.biblioteca.entity.Usuario saveUserInDB(Rol rol, String email) {
        com.example.biblioteca.entity.Usuario u = new com.example.biblioteca.entity.Usuario();
        u.setEmail(email);
        u.setNombre("Test User");
        u.setPassword("password123");
        u.setRol(rol);
        return usuarioRepository.save(u);
    }

}
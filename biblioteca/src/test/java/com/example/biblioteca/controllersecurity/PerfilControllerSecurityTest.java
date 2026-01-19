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

    // Inyecta los repositorios para preparar la BD
    @Autowired private com.example.biblioteca.repository.UsuarioRepository usuarioRepository;
    @Autowired private com.example.biblioteca.repository.PerfilRepository perfilRepository;

    private com.example.biblioteca.entity.Usuario saveUser(Rol rol, String email) {
        com.example.biblioteca.entity.Usuario u = new com.example.biblioteca.entity.Usuario();
        u.setNombre("Test");
        u.setEmail(email);
        u.setPassword("123456");
        u.setRol(rol);
        return usuarioRepository.save(u); // PERSISTIR EN BD
    }

    @Test
    void postPerfil_conRolAdmin_devuelve201() throws Exception {
        // 1. Guardar admin y el usuario al que pertenecerá el perfil
        com.example.biblioteca.entity.Usuario admin = saveUser(Rol.ROLE_ADMIN, "admin-p@test.com");
        com.example.biblioteca.entity.Usuario usuarioParaPerfil = saveUser(Rol.ROLE_USER, "user-p@test.com");

        String token = jwtUtil.generateToken(admin);

        // 2. Crear un DTO válido con el usuarioId
        com.example.biblioteca.dto.PerfilDTO dto = new com.example.biblioteca.dto.PerfilDTO();
        dto.setNickname("NickTest");
        dto.setUsuarioId(usuarioParaPerfil.getId()); // ID real de la BD

        mockMvc.perform(post("/api/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void postPerfil_conRolUser_devuelve201() throws Exception {
        // El usuario existe en la BD
        com.example.biblioteca.entity.Usuario user = saveUser(Rol.ROLE_USER, "user-crear@test.com");
        String token = jwtUtil.generateToken(user);

        com.example.biblioteca.dto.PerfilDTO dto = new com.example.biblioteca.dto.PerfilDTO();
        dto.setNickname("NickUser");
        dto.setUsuarioId(user.getId());

        mockMvc.perform(post("/api/perfiles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated()); // Ahora esperamos 201 porque TIENE permiso
    }

    @Test
    void deletePerfil_conRolAdmin_devuelve204() throws Exception {
        com.example.biblioteca.entity.Usuario admin = saveUser(Rol.ROLE_ADMIN, "admin-del-p@test.com");
        com.example.biblioteca.entity.Usuario u = saveUser(Rol.ROLE_USER, "u-del@test.com");

        // Creamos un perfil real para borrarlo
        com.example.biblioteca.entity.Perfil p = new com.example.biblioteca.entity.Perfil();
        p.setNickname("ABorrar");
        p.setUsuario(u);
        p = perfilRepository.save(p);

        String token = jwtUtil.generateToken(admin);

        mockMvc.perform(delete("/api/perfiles/" + p.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePerfil_conRolUser_devuelve403() throws Exception {
        // Usuario autenticado pero sin rol ADMIN
        com.example.biblioteca.entity.Usuario user = saveUser(Rol.ROLE_USER, "user-no-borrar@test.com");
        String token = jwtUtil.generateToken(user);

        mockMvc.perform(delete("/api/perfiles/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden()); // Aquí el AccessDeniedHandler enviará el 403 en lugar del 500
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
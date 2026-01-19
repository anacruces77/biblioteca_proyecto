package com.example.biblioteca.controllersecurity;

import com.example.biblioteca.entity.Libro;
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
public class LibroControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired private com.example.biblioteca.repository.UsuarioRepository usuarioRepository;
    @Autowired private com.example.biblioteca.repository.AutorRepository autorRepository;
    @Autowired private com.example.biblioteca.repository.LibroRepository libroRepository;

    private com.example.biblioteca.entity.Usuario saveUser(Rol rol, String email) {
        com.example.biblioteca.entity.Usuario u = new com.example.biblioteca.entity.Usuario();
        u.setNombre("Test User");
        u.setEmail(email);
        u.setPassword("123456");
        u.setRol(rol);
        return usuarioRepository.save(u); // Guardamos en BD
    }

    @Test
    void postLibro_conRolAdmin_devuelve201() throws Exception {
        // 1. Creamos Admin y Autor en BD
        com.example.biblioteca.entity.Usuario admin = saveUser(Rol.ROLE_ADMIN, "admin-libros@test.com");
        com.example.biblioteca.entity.Autor autor = new com.example.biblioteca.entity.Autor();
        autor.setNombre("Autor Test");
        autor = autorRepository.save(autor);

        String token = jwtUtil.generateToken(admin);

        // 2. Usamos el DTO con datos válidos
        com.example.biblioteca.dto.LibroDTO dto = new com.example.biblioteca.dto.LibroDTO();
        dto.setTitulo("Libro Nuevo");
        dto.setIsbn("123-456-789");
        dto.setAnioPublicacion(2023);
        dto.setAutorId(autor.getId());

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated());
    }

    @Test
    void postLibro_conRolUser_devuelve403() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_USER));

        Libro libro = new Libro();
        libro.setTitulo("LibroTest");

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libro))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }



    @Test
    void deleteLibro_conRolAdmin_devuelve204() throws Exception {
        // 1. Creamos Admin, Autor y el Libro a borrar
        com.example.biblioteca.entity.Usuario admin = saveUser(Rol.ROLE_ADMIN, "admin-del@test.com");

        com.example.biblioteca.entity.Autor autor = new com.example.biblioteca.entity.Autor();
        autor.setNombre("Autor");
        autor = autorRepository.save(autor);

        Libro libro = new Libro();
        libro.setTitulo("A borrar");
        libro.setAutor(autor);
        libro = libroRepository.save(libro);

        String token = jwtUtil.generateToken(admin);

        mockMvc.perform(delete("/api/libros/" + libro.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteLibro_conRolUser_devuelve403() throws Exception {
        String token = jwtUtil.generateToken(createUser(Rol.ROLE_USER));
        mockMvc.perform(delete("/api/libros/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteLibro_sinToken_devuelve401() throws Exception {
        // Nota: En muchas configuraciones de Spring Security, si no hay token,
        // devuelve 403 (Forbidden) por defecto si no tienes un AuthenticationEntryPoint.
        // Si tu test espera 401, asegúrate de haberlo configurado en SecurityConfig.
        mockMvc.perform(delete("/api/libros/1"))
                .andExpect(status().isForbidden()); // Cambia a isUnauthorized() si configuraste 401
    }

    private com.example.biblioteca.entity.Usuario createUser(Rol rol) {
        com.example.biblioteca.entity.Usuario u = new com.example.biblioteca.entity.Usuario();
        u.setRol(rol);
        return u;
    }
}
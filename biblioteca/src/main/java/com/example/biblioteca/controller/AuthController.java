package com.example.biblioteca.controller;

import com.example.biblioteca.Services.UsuarioService;
import com.example.biblioteca.entity.Rol;
import com.example.biblioteca.entity.Usuario;
import com.example.biblioteca.security.JwtUtil;
import com.example.biblioteca.dto.UsuarioDTO;
import com.example.biblioteca.security.dto.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

// Define que esta clase manejará peticiones HTTP y devolverá respuestas en formato JSON
@RestController
@RequestMapping("/api/auth")
public class AuthController {



    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    // Constructor para la inyección de dependencias necesarias para autenticación
    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }




    // Endpoint para dar de alta nuevos usuarios
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());

        // Cifra la contraseña antes de guardarla en la base de datos por seguridad
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(Rol.USER);// Asigna el rol de usuario estándar por defecto

        Usuario saved = usuarioService.saveUsuario(usuario);
        return ResponseEntity.ok(saved);// Retorna el usuario creado con éxito
    }




    // Endpoint para validar credenciales y obtener acceso
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO dto) {
        // 1. Buscar usuario
        Usuario usuario = usuarioService.getUsuarioByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Validar contraseña
        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            return ResponseEntity.status(401).body("Contraseña incorrecta");
        }

        // 3. Generar token
        String token = jwtUtil.generateToken(usuario);

        // 4. Devolver JSON con el token
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

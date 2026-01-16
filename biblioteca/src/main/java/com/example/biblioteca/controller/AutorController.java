package com.example.biblioteca.controller;

import com.example.biblioteca.Services.AutorService;
import com.example.biblioteca.Services.LibroService;
import com.example.biblioteca.Services.ResenaService;
import com.example.biblioteca.Services.UsuarioService;
import com.example.biblioteca.dto.AutorDTO;
import com.example.biblioteca.dto.ResenaDTO;
import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.entity.Libro;
import com.example.biblioteca.entity.Resena;
import com.example.biblioteca.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;


    // Inyectamos el service
    public AutorController(AutorService autorService) {
        this.autorService = autorService;

    }

    // GET /api/autores → listar autores
    @GetMapping
    public List<Autor> getAllAutores() {
        return autorService.getAllAutores();
    }

    // GET /api/autores/{id} → autor por id
    @GetMapping("/{id}")
    public ResponseEntity<Autor> getAutorById(@PathVariable Long id) {
        Optional<Autor> autor = autorService.getAutorById(id);
        return autor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/autores → crear autor
    @PostMapping
    public ResponseEntity<Autor> createAutor(
            @Valid @RequestBody AutorDTO dto) {

        Autor autor = new Autor();
        autor.setNombre(dto.getNombre());

        Autor saved = autorService.saveAutor(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    // DELETE /api/autores/{id} → eliminar autor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAutor(@PathVariable Long id) {
        autorService.deleteAutor(id);
        return ResponseEntity.noContent().build();
    }

}

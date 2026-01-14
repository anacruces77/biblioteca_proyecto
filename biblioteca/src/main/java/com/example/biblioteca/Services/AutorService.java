package com.example.biblioteca.Services;

import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService (AutorRepository autorRepository){
        // Inyectamos el repositorio de Autor
        this.autorRepository = autorRepository;
    }

    // Guardar o actualizar autor
    public Autor saveAutor(Autor autor) {
        return autorRepository.save(autor);
    }


    // Listar todos los autores
    public List<Autor> getAllAutores() {
        return autorRepository.findAll();
    }


    // Buscar autor por ID
    public Optional<Autor> getAutorById(Long id) {
        return autorRepository.findById(id);
    }


    // Eliminar autor por ID
    public void deleteAutor(Long id) {
        autorRepository.deleteById(id);
    }




}

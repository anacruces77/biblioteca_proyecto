package com.example.biblioteca.Services;

import com.example.biblioteca.entity.Autor;
import com.example.biblioteca.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marca la clase como un componente de servicio gestionado por Spring
public class AutorService {

    private final AutorRepository autorRepository;

    // Inyección de dependencias por constructor: asegura que el servicio tenga su repositorio
    public AutorService (AutorRepository autorRepository){
        // Inyectamos el repositorio de Autor
        this.autorRepository = autorRepository;
    }

    // Guarda un autor nuevo o actualiza uno existente si ya tiene ID
    public Autor saveAutor(Autor autor) {
        return autorRepository.save(autor);
    }


    // Recupera la lista completa de autores de la base de datos
    public List<Autor> getAllAutores() {
        return autorRepository.findAll();
    }


    // Busca un autor por ID; devuelve un Optional para manejar casos donde no exista
    public Optional<Autor> getAutorById(Long id) {
        return autorRepository.findById(id);
    }


    // Elimina el registro del autor por su clave primaria
    public void deleteAutor(Long id) {
        autorRepository.deleteById(id);
    }




}

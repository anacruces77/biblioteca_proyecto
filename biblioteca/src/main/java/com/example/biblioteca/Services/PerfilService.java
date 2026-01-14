package com.example.biblioteca.Services;


import com.example.biblioteca.entity.Perfil;
import com.example.biblioteca.repository.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;

    public PerfilService (PerfilRepository perfilRepository){

        // Inyectamos el repositorio de Perfil
        this.perfilRepository = perfilRepository;
    }


    // Guardar o actualizar perfil
    public Perfil savePerfil(Perfil perfil){
        return perfilRepository.save(perfil);
    }

    // Listar todos los perfiles
    public List<Perfil> getAllPerfiles(){
        return perfilRepository.findAll();
    }


    // Buscar perfil por ID
    public Optional<Perfil> getPerfilById(Long id){
        return perfilRepository.findById(id);
    }


    // Eliminar perfil por ID
    public void deletePerfil(Long id){
        perfilRepository.deleteById(id);
    }

}

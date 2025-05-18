package com.corhuila.app_movil_g2.Services;

import com.corhuila.app_movil_g2.Models.Persona;
import java.util.List;
import java.util.Optional;

public interface IPersonaService {
    List<Persona> findAll();
    Optional<Persona> findById(Long id);
    Persona save(Persona persona);
    void deleteById(Long id); // Delete físico para Persona, a menos que se implemente borrado lógico en cascada desde Usuario
}
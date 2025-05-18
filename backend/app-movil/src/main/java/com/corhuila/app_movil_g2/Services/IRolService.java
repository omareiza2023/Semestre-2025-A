package com.corhuila.app_movil_g2.Services;


import com.corhuila.app_movil_g2.Models.Rol;
import java.util.List;
import java.util.Optional;

public interface IRolService {
    List<Rol> findAll();
    Optional<Rol> findById(Long id);
    Rol save(Rol rol);
    void deleteById(Long id); // Delete físico, los roles no suelen borrarse lógicamente
    Optional<Rol> findByNombreRol(String nombreRol);
}

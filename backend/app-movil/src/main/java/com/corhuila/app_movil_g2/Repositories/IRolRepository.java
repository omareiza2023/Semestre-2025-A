package com.corhuila.app_movil_g2.Repositories;

import com.corhuila.app_movil_g2.Models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IRolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombreRol(String nombreRol);
}
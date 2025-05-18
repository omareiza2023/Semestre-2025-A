package com.corhuila.app_movil_g2.Repositories;

import com.corhuila.app_movil_g2.Models.CategoriaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ICategoriaServicioRepository extends JpaRepository<CategoriaServicio, Long> {
    List<CategoriaServicio> findByActivoTrue(); // Listar solo categorías activas
    Optional<CategoriaServicio> findByIdAndActivoTrue(Long id); // Buscar categoría activa por ID
}

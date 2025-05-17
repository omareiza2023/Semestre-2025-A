package com.corhuila.app_movil_g2.Repositories;

import com.corhuila.app_movil_g2.Models.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByActivoTrue(); // Listar solo servicios activos
    Optional<Servicio> findByIdAndActivoTrue(Long id); // Buscar servicio activo por ID
    List<Servicio> findByCategoriaServicioIdAndActivoTrue(Long categoriaId); // Listar servicios activos por categoría
}
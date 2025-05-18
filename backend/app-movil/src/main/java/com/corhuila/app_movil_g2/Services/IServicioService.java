package com.corhuila.app_movil_g2.Services;

import com.corhuila.app_movil_g2.Models.Servicio;
import java.util.List;
import java.util.Optional;

public interface IServicioService {
    List<Servicio> findAllActive(); // Listar solo activos
    List<Servicio> findAll(); // Listar todos

    Optional<Servicio> findById(Long id);
    Optional<Servicio> findByIdActive(Long id); // Buscar activo por ID

    Servicio save(Servicio servicio);

    void softDeleteById(Long id); // Borrado lógico

    List<Servicio> findByCategoriaIdActive(Long categoriaId); // Listar servicios activos por categoría
}

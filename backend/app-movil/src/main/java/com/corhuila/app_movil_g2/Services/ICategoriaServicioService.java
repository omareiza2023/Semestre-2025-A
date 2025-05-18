package com.corhuila.app_movil_g2.Services;

import com.corhuila.app_movil_g2.Models.CategoriaServicio;
import java.util.List;
import java.util.Optional;

public interface ICategoriaServicioService {
    List<CategoriaServicio> findAllActive(); // Listar solo activos
    List<CategoriaServicio> findAll(); // Listar todos

    Optional<CategoriaServicio> findById(Long id);
    Optional<CategoriaServicio> findByIdActive(Long id); // Buscar activo por ID

    CategoriaServicio save(CategoriaServicio categoriaServicio);

    void softDeleteById(Long id); // Borrado lógico
}

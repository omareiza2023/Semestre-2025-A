package com.corhuila.app_movil_g2.Services.impl;

import com.corhuila.app_movil_g2.Models.CategoriaServicio;
import com.corhuila.app_movil_g2.Repositories.ICategoriaServicioRepository;
import com.corhuila.app_movil_g2.Services.ICategoriaServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServicioServiceImpl implements ICategoriaServicioService {

    @Autowired
    private ICategoriaServicioRepository categoriaServicioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaServicio> findAllActive() {
        return categoriaServicioRepository.findByActivoTrue();
    }

     @Override
    @Transactional(readOnly = true)
    public List<CategoriaServicio> findAll() {
        return categoriaServicioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaServicio> findById(Long id) {
        return categoriaServicioRepository.findById(id);
    }

     @Override
    @Transactional(readOnly = true)
    public Optional<CategoriaServicio> findByIdActive(Long id) {
        return categoriaServicioRepository.findByIdAndActivoTrue(id);
    }


    @Override
    @Transactional
    public CategoriaServicio save(CategoriaServicio categoriaServicio) {
        return categoriaServicioRepository.save(categoriaServicio);
    }

    @Override
    @Transactional
    public void softDeleteById(Long id) {
        Optional<CategoriaServicio> categoriaOptional = categoriaServicioRepository.findById(id);
        if (categoriaOptional.isPresent()) {
            CategoriaServicio categoria = categoriaOptional.get();
            categoria.setActivo(false); // Marcar como inactiva
            categoriaServicioRepository.save(categoria); // Guardar el cambio de estado
        }
        // Opcional: lanzar excepción si no se encuentra la categoría
    }
}

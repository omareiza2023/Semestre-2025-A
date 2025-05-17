package com.corhuila.app_movil_g2.Services.impl;

import com.corhuila.app_movil_g2.Models.Servicio;
import com.corhuila.app_movil_g2.Repositories.IServicioRepository;
import com.corhuila.app_movil_g2.Services.IServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements IServicioService {

    @Autowired
    private IServicioRepository servicioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> findAllActive() {
        return servicioRepository.findByActivoTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> findById(Long id) {
        return servicioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> findByIdActive(Long id) {
        return servicioRepository.findByIdAndActivoTrue(id);
    }

    @Override
    @Transactional
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    @Transactional
    public void softDeleteById(Long id) {
        Optional<Servicio> servicioOptional = servicioRepository.findById(id);
        if (servicioOptional.isPresent()) {
            Servicio servicio = servicioOptional.get();
            servicio.setActivo(false); // Marcar como inactivo
            servicioRepository.save(servicio); // Guardar el cambio de estado
        }
        // Opcional: lanzar excepción si no se encuentra el servicio
    }

     @Override
    @Transactional(readOnly = true)
    public List<Servicio> findByCategoriaIdActive(Long categoriaId) {
        return servicioRepository.findByCategoriaServicioIdAndActivoTrue(categoriaId);
    }
}

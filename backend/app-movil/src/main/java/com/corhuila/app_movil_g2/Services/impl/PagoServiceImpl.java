package com.corhuila.app_movil_g2.Services.impl;

import com.corhuila.app_movil_g2.Models.Pago;
import com.corhuila.app_movil_g2.Repositories.IPagoRepository;
import com.corhuila.app_movil_g2.Services.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoRepository pagoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Pago> findAll() {
        return pagoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pago> findById(Long id) {
        return pagoRepository.findById(id);
    }

    @Override
    @Transactional
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        pagoRepository.deleteById(id); // Borrado físico
    }
}

package com.corhuila.app_movil_g2.Services.impl;

import com.corhuila.app_movil_g2.Models.Factura;
import com.corhuila.app_movil_g2.Repositories.IFacturaRepository;
import com.corhuila.app_movil_g2.Services.IFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaServiceImpl implements IFacturaService {

    @Autowired
    private IFacturaRepository facturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Factura> findById(Long id) {
        return facturaRepository.findById(id);
    }

    @Override
    @Transactional
    public Factura save(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        facturaRepository.deleteById(id); // Borrado físico
    }

    @Override
    @Transactional(readOnly = true)
    public List<Factura> findByUsuarioId(Long usuarioId) {
        return facturaRepository.findByUsuarioId(usuarioId);
    }
}

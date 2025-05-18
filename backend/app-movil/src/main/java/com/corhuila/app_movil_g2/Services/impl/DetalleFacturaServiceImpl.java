package com.corhuila.app_movil_g2.Services.impl;

import com.corhuila.app_movil_g2.Models.DetalleFactura;
import com.corhuila.app_movil_g2.Repositories.IDetalleFacturaRepository;
import com.corhuila.app_movil_g2.Services.IDetalleFacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleFacturaServiceImpl implements IDetalleFacturaService {

    @Autowired
    private IDetalleFacturaRepository detalleFacturaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DetalleFactura> findAll() {
        return detalleFacturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleFactura> findById(Long id) {
        return detalleFacturaRepository.findById(id);
    }

    @Override
    @Transactional
    public DetalleFactura save(DetalleFactura detalleFactura) {
        return detalleFacturaRepository.save(detalleFactura);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        detalleFacturaRepository.deleteById(id); // Borrado físico
    }

     @Override
    @Transactional(readOnly = true)
    public List<DetalleFactura> findByFacturaId(Long facturaId) {
        return detalleFacturaRepository.findByFacturaId(facturaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetalleFactura> findByReservaId(Long reservaId) {
        return detalleFacturaRepository.findByReservaId(reservaId);
    }
}

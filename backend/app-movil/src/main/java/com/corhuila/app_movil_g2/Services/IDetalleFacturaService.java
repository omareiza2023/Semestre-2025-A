package com.corhuila.app_movil_g2.Services;

import com.corhuila.app_movil_g2.Models.DetalleFactura;
import java.util.List;
import java.util.Optional;

public interface IDetalleFacturaService {
    List<DetalleFactura> findAll();
    Optional<DetalleFactura> findById(Long id);
    DetalleFactura save(DetalleFactura detalleFactura);
    void deleteById(Long id); // Delete físico
    List<DetalleFactura> findByFacturaId(Long facturaId); // Buscar detalles por factura
    List<DetalleFactura> findByReservaId(Long reservaId); // Buscar detalles por reserva
}

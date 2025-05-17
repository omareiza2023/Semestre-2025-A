package com.corhuila.app_movil_g2.Repositories;

import com.corhuila.app_movil_g2.Models.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IDetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {
    List<DetalleFactura> findByFacturaId(Long facturaId); // Buscar detalles por factura
    List<DetalleFactura> findByReservaId(Long reservaId); // Buscar detalles por reserva
}

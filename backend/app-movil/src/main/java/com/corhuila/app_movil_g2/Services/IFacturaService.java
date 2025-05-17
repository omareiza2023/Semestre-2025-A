package com.corhuila.app_movil_g2.Services;

import com.corhuila.app_movil_g2.Models.Factura;
import java.util.List;
import java.util.Optional;

public interface IFacturaService {
    List<Factura> findAll();
    Optional<Factura> findById(Long id);
    Factura save(Factura factura);
    void deleteById(Long id); // Delete físico (o lógico si DetalleFactura también lo es)
    List<Factura> findByUsuarioId(Long usuarioId); // Buscar facturas por usuario
}

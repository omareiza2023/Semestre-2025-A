package com.corhuila.app_movil_g2.Repositories;

import com.corhuila.app_movil_g2.Models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IFacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByUsuarioId(Long usuarioId); // Buscar facturas por usuario
}

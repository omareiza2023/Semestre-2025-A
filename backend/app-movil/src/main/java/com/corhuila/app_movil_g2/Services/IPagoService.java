package com.corhuila.app_movil_g2.Services;


import com.corhuila.app_movil_g2.Models.Pago;
import java.util.List;
import java.util.Optional;

public interface IPagoService {
    List<Pago> findAll();
    Optional<Pago> findById(Long id);
    Pago save(Pago pago);
    void deleteById(Long id); // Delete físico
}

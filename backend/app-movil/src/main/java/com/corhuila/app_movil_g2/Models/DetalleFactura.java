package com.corhuila.app_movil_g2.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "detallefactura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFactura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Reserva
    @ManyToOne
    @JoinColumn(name = "idReserva", nullable = false)
    private Reserva reserva;

    // Relación con Factura
    @ManyToOne
    @JoinColumn(name = "idFactura", nullable = false)
    private Factura factura;

    // Relación con Pago
    @ManyToOne
    @JoinColumn(name = "idPago", nullable = false)
    private Pago pago;
}

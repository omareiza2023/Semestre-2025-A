package com.corhuila.app_movil_g2.Models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pago")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String tipoPago; // Ej: "Tarjeta", "Efectivo", "Transferencia"

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private LocalDateTime fechaPago;

    // Relación inversa con DetalleFactura (un Pago puede estar en muchos DetalleFactura - aunque usualmente es 1 a 1)
    @OneToMany(mappedBy = "pago")
    private List<DetalleFactura> detallesFactura;
}
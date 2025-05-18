package com.corhuila.app_movil_g2.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Usuario (una Factura pertenece a un Usuario)
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime fecha;

    // Relación inversa con DetalleFactura (una Factura puede tener muchos DetalleFactura)
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true) // Cascade para guardar detalles con la factura
    private List<DetalleFactura> detallesFactura;
}

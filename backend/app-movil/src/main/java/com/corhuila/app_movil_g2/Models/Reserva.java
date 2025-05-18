package com.corhuila.app_movil_g2.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Usuario (una Reserva pertenece a un Usuario - Cliente o Barbero)
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario; // Puede ser el cliente o el barbero asignado

    // Relación con Servicio (una Reserva es para un Servicio)
    @ManyToOne
    @JoinColumn(name = "idServicio", nullable = false)
    private Servicio servicio;

    @Column(nullable = false)
    private LocalDateTime fecha; // Fecha y hora de la reserva

    // Campo para borrado lógico o estado de la reserva (pendiente, confirmada, cancelada, completada)
    @Column(nullable = false, length = 20)
    private String estado; // Ej: "ACTIVA", "CANCELADA", "COMPLETADA", "INACTIVA" (para borrado lógico)

    // Relación inversa con DetalleFactura (una Reserva puede estar en muchos DetalleFactura - usualmente 1 a 1)
    @OneToMany(mappedBy = "reserva")
    private List<DetalleFactura> detallesFactura;

    // Constructor para facilitar la creación con estado inicial
     public Reserva(Usuario usuario, Servicio servicio, LocalDateTime fecha, String estado) {
        this.usuario = usuario;
        this.servicio = servicio;
        this.fecha = fecha;
        this.estado = estado;
    }
}
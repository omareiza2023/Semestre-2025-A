package com.corhuila.app_movil_g2.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "horario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Usuario (un Horario pertenece a un Usuario - Barbero)
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario; // Este usuario debe ser un Barbero

    @Column(nullable = false, length = 10)
    private String diasSemana; // Ej: "LUNES", "MARTES", etc. O usar un enfoque más flexible

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    // Campo para borrado lógico o estado (activo, inactivo)
    @Column(nullable = false, length = 20)
    private String estado; // Ej: "ACTIVO", "INACTIVO"
}

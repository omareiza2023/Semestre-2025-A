package com.corhuila.app_movil_g2.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "servicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Servicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = true, columnDefinition = "TEXT") // Descripción como TEXT, puede ser nula
    private String descripcion;

    // Relación con CategoriaServicio (un Servicio pertenece a una CategoriaServicio)
    @ManyToOne
    @JoinColumn(name = "idcategoriaservicio", nullable = false)
    private CategoriaServicio categoriaServicio;

    // Campo para borrado lógico
    @Column(nullable = false)
    private boolean activo = true; // Por defecto activo

    // Relación inversa con Reserva (un Servicio puede tener muchas Reservas)
    @OneToMany(mappedBy = "servicio")
    private List<Reserva> reservas;
}

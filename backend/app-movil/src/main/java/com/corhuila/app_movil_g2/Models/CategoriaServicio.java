package com.corhuila.app_movil_g2.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "categoriaservicio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaServicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2) // BigDecimal para precio
    private BigDecimal precio;

    // Campo para borrado lógico
    @Column(nullable = false)
    private boolean activo = true; // Por defecto activo

    // Relación inversa con Servicio (una CategoriaServicio puede tener muchos Servicios)
    @OneToMany(mappedBy = "categoriaServicio")
    private List<Servicio> servicios;
}
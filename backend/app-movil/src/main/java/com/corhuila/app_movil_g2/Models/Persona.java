package com.corhuila.app_movil_g2.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = true, length = 10, unique = true) // Documento podría ser nulo o no único dependiendo del caso
    private String documento;

    @Column(nullable = true, length = 10) // Teléfono podría ser nulo
    private String telefono;

    // Relación con Usuario (una Persona puede tener uno o más Usuarios si se permite)
    // La relación en el esquema es ManyToOne de Usuario a Persona, por lo que en Persona es OneToMany
    @OneToMany(mappedBy = "persona")
    private List<Usuario> usuarios;
}

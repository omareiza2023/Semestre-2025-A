package com.corhuila.app_movil_g2.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "rol")
@Data // Lombok: Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Genera constructor sin argumentos
@AllArgsConstructor // Lombok: Genera constructor con todos los argumentos
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String nombreRol;

    // Relación con Usuario (un Rol puede tener muchos Usuarios)
    @OneToMany(mappedBy = "rol")
    private List<Usuario> usuarios;
}

package com.corhuila.app_movil_g2.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements Serializable, UserDetails { // Implementa UserDetails para Spring Security

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username; // Usado como nombre de usuario para login

    @Column(nullable = false) // La longitud dependerá del hash (BCrypt es 60)
    private String password;

    // Relación con Persona (un Usuario pertenece a una Persona)
    @ManyToOne
    @JoinColumn(name = "idPersona", nullable = false)
    private Persona persona;

    // Relación con Rol (un Usuario tiene un Rol)
    // Ajustamos la relación para que sea por ID de Rol, no por nombreRol
    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    // Campo para borrado lógico
    @Column(nullable = false)
    private boolean activo = true; // Por defecto activo

    // Relaciones inversas (un Usuario puede tener muchas Reservas, Facturas, Horarios)
    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "usuario")
    private List<Factura> facturas;

    @OneToMany(mappedBy = "usuario")
    private List<Horario> horarios;

    // Métodos de UserDetails (requeridos por Spring Security)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Asigna el rol del usuario como autoridad
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol.getNombreRol().toUpperCase()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // O implementa lógica de expiración si es necesario
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // O implementa lógica de bloqueo si es necesario
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // O implementa lógica de expiración de credenciales
    }

    @Override
    public boolean isEnabled() {
        return this.activo; // Usa el campo activo para determinar si el usuario está habilitado
    }

    // NOTA: getUsername() y getPassword() son generados por Lombok @Data
}

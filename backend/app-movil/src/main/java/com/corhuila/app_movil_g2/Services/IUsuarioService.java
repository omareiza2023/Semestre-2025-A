package com.corhuila.app_movil_g2.Services;

import com.corhuila.app_movil_g2.Models.Usuario;
import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService extends UserDetailsService { // Implementa UserDetailsService

    List<Usuario> findAllActive(); // Listar solo activos
    List<Usuario> findAll(); // Listar todos (activos e inactivos)

    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByIdActive(Long id); // Buscar activo por ID

    Usuario save(Usuario usuario); // Guardar usuario (con contraseña ya hasheada)
    Usuario registerUser(Usuario usuario); // Registrar nuevo usuario (hashea contraseña y asigna rol por defecto)

    void softDeleteById(Long id); // Borrado lógico

    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);

    // Métodos de correo y recuperación de contraseña (adaptados)
    void sendEmail(String to, String subject, String body) throws MessagingException;
    String generarContraseniaAleatoria();
    Optional<Usuario> findByEmail(String email); // Asumimos que 'email' se mapea a un campo en Persona o Usuario (usaremos username por ahora)

    // Nuevos métodos según roles/necesidades
    List<Usuario> findBarberosActive(); // Buscar usuarios con rol BARBERO y activos
    List<Usuario> findClientesActive(); // Buscar usuarios con rol CLIENTE y activos

    String recuperarContraseña(String correo) throws MessagingException;
}

package com.corhuila.app_movil_g2.Repositories;

import com.corhuila.app_movil_g2.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByPersonaDocumento(String documento); // Buscar usuario por documento de persona
    Optional<Usuario> findByPersonaTelefono(String telefono); // Buscar usuario por teléfono de persona
    List<Usuario> findByActivoTrue(); // Listar solo usuarios activos
    Optional<Usuario> findByIdAndActivoTrue(Long id); // Buscar usuario activo por ID
    boolean existsByUsername(String username);
    List<Usuario> findByRolNombreRol(String rolNombre); // Buscar usuarios por nombre de rol
    List<Usuario> findByRolNombreRolAndActivoTrue(String rolNombre); // Buscar usuarios activos por nombre de rol
}

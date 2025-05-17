package com.corhuila.app_movil_g2.Repositories;

import com.corhuila.app_movil_g2.Models.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface IHorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByEstado(String estado); // Listar por estado
    List<Horario> findByEstadoNot(String estado); // Listar por estado excluyendo uno
    Optional<Horario> findByIdAndEstadoNot(Long id, String estado); // Buscar activo por ID

    List<Horario> findByUsuarioIdAndEstadoNot(Long usuarioId, String estado); // Horarios activos de un barbero
    List<Horario> findByUsuarioIdAndDiasSemanaAndEstadoNot(Long usuarioId, String diasSemana, String estado); // Horarios activos de un barbero para un día
}

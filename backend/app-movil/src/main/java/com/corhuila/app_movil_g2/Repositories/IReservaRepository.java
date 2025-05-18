package com.corhuila.app_movil_g2.Repositories;

import com.corhuila.app_movil_g2.Models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByEstado(String estado); // Listar por estado (incluye "INACTIVA" para borrado lógico)
    List<Reserva> findByEstadoNot(String estado); // Listar por estado excluyendo uno (útil para activos)
    List<Reserva> findByEstadoNotOrderByFechaAsc(String estado); // Listar activos ordenados por fecha

    Optional<Reserva> findByIdAndEstadoNot(Long id, String estado); // Buscar activa por ID

    List<Reserva> findByUsuarioIdAndEstadoNotOrderByFechaAsc(Long usuarioId, String estado); // Buscar reservas activas de un usuario
    List<Reserva> findByUsuarioIdAndFechaBetweenAndEstadoNot(Long usuarioId, LocalDateTime start, LocalDateTime end, String estado); // Buscar reservas activas de un usuario en un rango de fecha/hora

    List<Reserva> findByServicioIdAndEstadoNotOrderByFechaAsc(Long servicioId, String estado); // Buscar reservas activas por servicio

    List<Reserva> findByFechaBetweenAndEstadoNot(LocalDateTime start, LocalDateTime end, String estado); // Buscar reservas activas en un rango de fecha/hora (para disponibilidad)

    // Métodos útiles para verificar disponibilidad
    boolean existsByUsuarioIdAndFechaAndEstadoNot(Long usuarioId, LocalDateTime fecha, String estado); // Ya tiene una reserva a esa hora?
    boolean existsByUsuarioIdAndFechaBetweenAndEstadoNot(Long usuarioId, LocalDateTime start, LocalDateTime end, String estado); // Ya tiene una reserva en ese rango?
    boolean existsByUsuarioIdAndFecha(Long usuarioId, LocalDateTime fecha); // ¿Existe una reserva (activa o inactiva) a esa hora para este usuario?

}

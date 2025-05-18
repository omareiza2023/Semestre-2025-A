package com.corhuila.app_movil_g2.Services;

import com.corhuila.app_movil_g2.Models.Reserva;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IReservaService {
    List<Reserva> findAllActive(); // Listar solo activas (estado != "CANCELADA" y != "INACTIVA")
    List<Reserva> findAll(); // Listar todas

    Optional<Reserva> findById(Long id);
    Optional<Reserva> findByIdActive(Long id); // Buscar activa por ID

    Reserva save(Reserva reserva); // Guardar o actualizar reserva
    Reserva createReserva(Reserva reserva); // Lógica específica para crear una nueva reserva (validaciones, estado inicial)

    void softDeleteById(Long id); // Cambiar estado a "INACTIVA"

    List<Reserva> findByUsuarioIdActive(Long usuarioId); // Reservas activas de un usuario
    List<Reserva> findByBarberoIdAndDateRangeActive(Long barberoId, LocalDateTime start, LocalDateTime end); // Reservas activas de un barbero en un rango de fecha/hora

    boolean isSlotAvailable(Long barberoId, LocalDateTime fechaHoraInicio); // Verificar disponibilidad del barbero
    boolean userHasConflictingReservation(Long usuarioId, LocalDateTime fechaHoraInicio); // Verificar si el cliente ya tiene reserva a esa hora
}

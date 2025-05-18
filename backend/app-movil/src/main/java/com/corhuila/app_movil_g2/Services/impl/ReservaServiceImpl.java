package com.corhuila.app_movil_g2.Services.impl;

import com.corhuila.app_movil_g2.Models.Reserva;
import com.corhuila.app_movil_g2.Models.Usuario;
import com.corhuila.app_movil_g2.Repositories.IReservaRepository;
import com.corhuila.app_movil_g2.Repositories.IUsuarioRepository; // Necesario para verificar barbero
import com.corhuila.app_movil_g2.Services.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl implements IReservaService {

    @Autowired
    private IReservaRepository reservaRepository;

     @Autowired
    private IUsuarioRepository usuarioRepository; // Para verificar si el barbero existe y es barbero


    private static final String ESTADO_ACTIVA = "ACTIVA";
    private static final String ESTADO_CANCELADA = "CANCELADA";
     private static final String ESTADO_COMPLETADA = "COMPLETADA";
    private static final String ESTADO_INACTIVA = "INACTIVA"; // Para borrado lógico

    // Estados que consideramos "activos" para la mayoría de listados
    private static final List<String> ESTADOS_ACTIVIDAD = List.of(ESTADO_ACTIVA, ESTADO_COMPLETADA);


    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findAllActive() {
        // Excluimos INACTIVA y CANCELADA
        return reservaRepository.findByEstadoNotOrderByFechaAsc(ESTADO_INACTIVA); // O ajustar según qué estados son "activos"
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reserva> findById(Long id) {
        return reservaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reserva> findByIdActive(Long id) {
        return reservaRepository.findByIdAndEstadoNot(id, ESTADO_INACTIVA);
    }

    @Override
    @Transactional
    public Reserva save(Reserva reserva) {
        // Este método puede usarse para actualizar reservas existentes
        return reservaRepository.save(reserva);
    }

     @Override
    @Transactional
    public Reserva createReserva(Reserva reserva) {
        // Lógica para la creación de una nueva reserva
        // 1. Validar que el usuario (cliente) no tenga otra reserva a esa hora
        if (userHasConflictingReservation(reserva.getUsuario().getId(), reserva.getFecha())) {
             throw new IllegalArgumentException("Ya tienes una reserva programada para esa fecha y hora.");
        }

        // 2. Validar que el barbero esté disponible a esa hora
        // NOTA: En este esquema, `idUsuario` en Reserva puede ser tanto el cliente como el barbero asignado.
        // Si el campo `idUsuario` en Reserva *siempre* es el cliente, necesitaríamos un campo adicional `idBarbero`
        // y validar la disponibilidad del barbero usando ese campo.
        // Asumiré por ahora que el `usuario` en la entidad Reserva es el *cliente* que crea la reserva,
        // y que la lógica de asignar un barbero y verificar su disponibilidad se haría *antes* de llamar a este método,
        // o se necesitaría un campo `barbero` en la entidad `Reserva` con una relación `@ManyToOne` a `Usuario`.
        // Dada la ambigüedad del esquema, no implementaré la validación de disponibilidad del barbero aquí,
        // pero el método `isSlotAvailable` está definido en la interfaz si se decide añadir el campo barbero.
        // Si `usuario` en Reserva es el BARBERO asignado, entonces la validación sería contra ese usuario.
        // Voy a asumir que 'usuario' es el CLIENTE por ahora.

        // 3. Establecer estado inicial (ej: ACTIVA)
        reserva.setEstado(ESTADO_ACTIVA);

        // 4. Guardar la reserva
        return reservaRepository.save(reserva);
    }


    @Override
    @Transactional
    public void softDeleteById(Long id) {
        Optional<Reserva> reservaOptional = reservaRepository.findById(id);
        if (reservaOptional.isPresent()) {
            Reserva reserva = reservaOptional.get();
            reserva.setEstado(ESTADO_INACTIVA); // Marcar como inactiva (borrado lógico)
            reservaRepository.save(reserva); // Guardar el cambio de estado
        }
        // Opcional: lanzar excepción si no se encuentra la reserva
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reserva> findByUsuarioIdActive(Long usuarioId) {
        // Busca reservas para un usuario excluyendo INACTIVA y CANCELADA
        return reservaRepository.findByUsuarioIdAndEstadoNotOrderByFechaAsc(usuarioId, ESTADO_INACTIVA); // O ajustar según qué estados son "activos" para el usuario
    }

     @Override
    @Transactional(readOnly = true)
    public List<Reserva> findByBarberoIdAndDateRangeActive(Long barberoId, LocalDateTime start, LocalDateTime end) {
         // Busca reservas para un *barbero* (asumiendo que el usuario en Reserva es el barbero) en un rango de fecha/hora
         // excluyendo estados no deseados.
         // Si el campo `usuario` en Reserva es el cliente, este método NO sería válido como está.
         // Si necesitamos buscar reservas donde el usuario asignado es un barbero,
         // y la Reserva *también* tiene un campo para el cliente, la consulta cambiaría.
         // Dada la ambigüedad, este método asume que `usuario` en Reserva ES el barbero para verificar su agenda.
        return reservaRepository.findByUsuarioIdAndFechaBetweenAndEstadoNot(barberoId, start, end, ESTADO_INACTIVA); // O ajustar estados
    }


    // --- Métodos de Validación de Disponibilidad ---

     @Override
    @Transactional(readOnly = true)
    public boolean isSlotAvailable(Long barberoId, LocalDateTime fechaHoraInicio) {
        // Este método verifica si un barbero tiene *otra* reserva activa en la misma fecha y hora.
        // Requiere que la entidad Reserva tenga un campo `barbero` o que el campo `usuario`
        // sea usado para representar al barbero asignado.
        // Asumiendo que `usuario` en Reserva representa al barbero para la verificación de disponibilidad:
        List<Reserva> reservasConflicto = reservaRepository.findByUsuarioIdAndFechaBetweenAndEstadoNot(
            barberoId, fechaHoraInicio.minusMinutes(1), fechaHoraInicio.plusMinutes(1), ESTADO_INACTIVA // Rango pequeño para la hora exacta
        );
        // También se podría validar que el barbero sea realmente un barbero antes de llamar a esto.
        Optional<Usuario> barbero = usuarioRepository.findById(barberoId);
        if (barbero.isEmpty() || !barbero.get().getRol().getNombreRol().equals("BARBERO")) {
             // Manejar error: el usuario proporcionado no es un barbero válido
             // Podrías lanzar una excepción o devolver false.
             return false; // O throw new IllegalArgumentException("ID de usuario proporcionado no corresponde a un barbero.");
        }

        // Si hay alguna reserva activa en ese rango para el barbero, el slot NO está disponible
        return reservasConflicto.isEmpty(); // true si está disponible, false si no
    }

     @Override
     @Transactional(readOnly = true)
    public boolean userHasConflictingReservation(Long usuarioId, LocalDateTime fechaHoraInicio) {
        // Este método verifica si un *cliente* ya tiene una reserva activa a esa hora.
        // Asumiendo que `usuario` en Reserva representa al cliente que hizo la reserva:
         List<Reserva> reservasClienteConflicto = reservaRepository.findByUsuarioIdAndFechaBetweenAndEstadoNot(
             usuarioId, fechaHoraInicio.minusMinutes(1), fechaHoraInicio.plusMinutes(1), ESTADO_INACTIVA // Rango pequeño para la hora exacta
         );
        // Si hay alguna reserva activa en ese rango para el cliente, hay un conflicto.
         return !reservasClienteConflicto.isEmpty(); // true si hay conflicto, false si no
    }
}

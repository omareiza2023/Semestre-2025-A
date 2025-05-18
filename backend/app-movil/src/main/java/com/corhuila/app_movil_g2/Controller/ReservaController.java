package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.*;
import com.corhuila.app_movil_g2.Models.Reserva;
import com.corhuila.app_movil_g2.Models.Servicio;
import com.corhuila.app_movil_g2.Models.Usuario;
import com.corhuila.app_movil_g2.Services.IReservaService;
import com.corhuila.app_movil_g2.Services.IServicioService;
import com.corhuila.app_movil_g2.Services.IUsuarioService;
import com.corhuila.app_movil_g2.util.AppConstants; // Asegúrate de tener esta clase de constantes
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private IReservaService reservaService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IServicioService servicioService;

    // --- Mappers (Simplificados) ---
    private UsuarioDTO convertUsuarioToDTO(Usuario usuario) {
        if (usuario == null) return null;
        PersonaDTO personaDTO = (usuario.getPersona() != null) ?
                new PersonaDTO(usuario.getPersona().getId(), usuario.getPersona().getNombre(), usuario.getPersona().getApellido(), usuario.getPersona().getDocumento(), usuario.getPersona().getTelefono())
                : null;
        RolDTO rolDTO = (usuario.getRol() != null) ?
                new RolDTO(usuario.getRol().getId(), usuario.getRol().getNombreRol())
                : null;
        return new UsuarioDTO(usuario.getId(), usuario.getUsername(), personaDTO, rolDTO, usuario.isActivo());
    }

    private ServicioDTO convertServicioToDTO(Servicio servicio) {
        if (servicio == null) return null;
        CategoriaServicioDTO catDTO = (servicio.getCategoriaServicio() != null) ?
                new CategoriaServicioDTO(servicio.getCategoriaServicio().getId(), servicio.getCategoriaServicio().getNombre(), servicio.getCategoriaServicio().getPrecio(), servicio.getCategoriaServicio().isActivo())
                : null;
        return new ServicioDTO(servicio.getId(), servicio.getNombre(), servicio.getDescripcion(), catDTO, servicio.isActivo());
    }

    private ReservaDTO convertToDTO(Reserva reserva) {
        if (reserva == null) return null;
        return new ReservaDTO(
                reserva.getId(),
                convertUsuarioToDTO(reserva.getUsuario()), // Asumiendo que Reserva.usuario es el cliente
                convertServicioToDTO(reserva.getServicio()),
                reserva.getFecha(),
                reserva.getEstado()
        );
    }
    // --- Fin Mappers ---

    @PostMapping
    // El cliente crea para sí mismo, un admin/barbero podría crear para un cliente (requiere lógica adicional de permisos y DTO)
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN', 'BARBERO')")
    public ResponseEntity<ReservaDTO> createReserva(@Valid @RequestBody ReservaCreateDTO createDTO) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();
    final Usuario currentUser = usuarioService.findByUsername(currentUsername)
        .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Usuario autenticado no encontrado."));

    // Usa una nueva variable para que sea efectivamente final
    final Long resolvedClienteId;
    if (currentUser.getRol().getNombreRol().equals("CLIENTE")) {
        resolvedClienteId = currentUser.getId(); // Solo puede reservar para sí mismo
    } else if (createDTO.getIdUsuarioCliente() == null) {
        throw new GlobalExceptionHandler.BusinessLogicException("Se debe especificar el ID del cliente para la reserva.");
    } else {
        resolvedClienteId = createDTO.getIdUsuarioCliente(); // ADMIN o BARBERO
    }

    Usuario cliente = usuarioService.findByIdActive(resolvedClienteId)
        .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Cliente activo no encontrado con ID: " + resolvedClienteId));
        Servicio servicio = servicioService.findByIdActive(createDTO.getIdServicio())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Servicio activo no encontrado con ID: " + createDTO.getIdServicio()));

        // Lógica de validación de disponibilidad (simplificada aquí, el servicio la tiene más completa)
        // Aquí es donde se necesitaría el ID del barbero si el cliente lo elige o si el sistema lo asigna
        // y se llamaría a reservaService.isSlotAvailable(barberoId, createDTO.getFecha())
        // Por ahora, solo validamos conflicto del cliente.
        if (reservaService.userHasConflictingReservation(cliente.getId(), createDTO.getFecha())) {
            throw new GlobalExceptionHandler.BusinessLogicException("El cliente ya tiene una reserva en conflicto para esa fecha y hora.");
        }
        // IMPORTANTE: La lógica de disponibilidad del barbero (isSlotAvailable) debe invocarse aquí.
        // Esto requiere saber qué barbero realizará el servicio.
        // Si el DTO tuviera idBarbero, se pasaría a isSlotAvailable.
        // Por simplicidad, se omite esta llamada directa, asumiendo que el servicio la manejará o que el flujo es más complejo.


        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setUsuario(cliente); // Reserva.usuario es el CLIENTE
        nuevaReserva.setServicio(servicio);
        nuevaReserva.setFecha(createDTO.getFecha());
        // El estado inicial se establece en el servicio (createReserva)

        Reserva reservaGuardada = reservaService.createReserva(nuevaReserva);
        return new ResponseEntity<>(convertToDTO(reservaGuardada), HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{usuarioId}/activas")
    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('CLIENTE','BARBERO') and #usuarioId == principal.id)")
    public ResponseEntity<List<ReservaDTO>> getReservasActivasByUsuario(@PathVariable Long usuarioId) {
        // Validar que el usuario exista
        usuarioService.findByIdActive(usuarioId)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Usuario activo no encontrado con ID: " + usuarioId));

        List<ReservaDTO> reservas = reservaService.findByUsuarioIdActive(usuarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservas);
    }
    
    @GetMapping("/mis-reservas/activas")
    @PreAuthorize("hasAnyRole('CLIENTE', 'BARBERO')") // Un barbero también es un usuario y podría tener "sus" reservas como cliente
    public ResponseEntity<List<ReservaDTO>> getMisReservasActivas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Usuario currentUser = (Usuario) authentication.getPrincipal();
        
        List<ReservaDTO> reservas = reservaService.findByUsuarioIdActive(currentUser.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservas);
    }

    // Este endpoint asume que el servicio puede encontrar reservas asignadas a un barbero.
    // La entidad Reserva actual (solo un campo Usuario) hace esto complejo si ese campo es siempre el cliente.
    // Se necesitaría un campo 'barberoAsignado' en Reserva o una tabla de enlace Reserva-Barbero.
    @GetMapping("/barbero/{barberoId}/rango")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and #barberoId == principal.id)")
    public ResponseEntity<List<ReservaDTO>> getReservasByBarberoAndDateRange(
            @PathVariable Long barberoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        
        usuarioService.findByIdActive(barberoId) //Verifica que el barbero exista
            .filter(u -> u.getRol().getNombreRol().equals("BARBERO"))
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Barbero activo no encontrado con ID: " + barberoId));

        LocalDateTime inicio = fechaInicio.atStartOfDay();
        LocalDateTime fin = fechaFin.atTime(LocalTime.MAX);

        List<ReservaDTO> reservas = reservaService.findByBarberoIdAndDateRangeActive(barberoId, inicio, fin).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Lógica más fina dentro del método
    public ResponseEntity<ReservaDTO> getReservaById(@PathVariable Long id) {
        Reserva reserva = reservaService.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Usuario currentUser = (Usuario) authentication.getPrincipal();

        // Permitir si es ADMIN, o si es el usuario de la reserva (cliente), o si es el barbero asignado (si se pudiera determinar)
        boolean isAdmin = currentUser.getRol().getNombreRol().equals("ADMIN");
        boolean isOwner = reserva.getUsuario().getId().equals(currentUser.getId());
        // boolean isAssignedBarber = ...; // Lógica para determinar si el currentUser es el barbero asignado a esta reserva

        if (!isAdmin && !isOwner /* && !isAssignedBarber */) {
            throw new GlobalExceptionHandler.BusinessLogicException("No tiene permiso para ver esta reserva.");
        }
        return ResponseEntity.ok(convertToDTO(reserva));
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservaDTO> updateEstadoReserva(@PathVariable Long id, @Valid @RequestBody ReservaUpdateEstadoDTO estadoDTO) {
        Reserva reserva = reservaService.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Reserva no encontrada con ID: " + id));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Usuario currentUser = (Usuario) authentication.getPrincipal();
        String userRole = currentUser.getRol().getNombreRol();

        // Lógica de permisos para cambiar estado
        boolean canUpdate = false;
        if (userRole.equals("ADMIN")) {
            canUpdate = true;
        } else if (userRole.equals("CLIENTE") && reserva.getUsuario().getId().equals(currentUser.getId())) {
            // Cliente solo puede cancelar sus propias reservas (y quizás solo si está "ACTIVA")
            if (estadoDTO.getNuevoEstado().equals(AppConstants.ESTADO_CANCELADA) && reserva.getEstado().equals(AppConstants.ESTADO_ACTIVA)) {
                // Podría haber lógica de tiempo de antelación para cancelar
                canUpdate = true;
            }
        } else if (userRole.equals("BARBERO")) {
            // Barbero puede completar o cancelar reservas (si están asignadas a él)
             // if (isAssignedBarber && (estadoDTO.getNuevoEstado().equals(AppConstants.ESTADO_COMPLETADA) || estadoDTO.getNuevoEstado().equals(AppConstants.ESTADO_CANCELADA))) {
            //    canUpdate = true;
            // }
            // Simplificación: Barbero puede cambiar a completada o cancelada (requiere lógica de asignación)
            if (estadoDTO.getNuevoEstado().equals(AppConstants.ESTADO_COMPLETADA) || estadoDTO.getNuevoEstado().equals(AppConstants.ESTADO_CANCELADA)){
                // Aquí faltaría la validación de que el barbero está asignado a esta reserva.
                // Por ahora, un barbero genérico puede hacerlo si la lógica de asignación no está en el modelo.
                // Esto es una simplificación y debe revisarse.
                canUpdate = true; // PELIGROSO SIN VERIFICAR ASIGNACIÓN
            }
        }

        if (!canUpdate) {
            throw new GlobalExceptionHandler.BusinessLogicException("No tiene permiso para cambiar el estado de esta reserva a '" + estadoDTO.getNuevoEstado() + "'.");
        }
        
        // Validar transición de estado si es necesario (ej. no se puede completar una reserva cancelada)
        // ...

        reserva.setEstado(estadoDTO.getNuevoEstado());
        Reserva reservaActualizada = reservaService.save(reserva);
        return ResponseEntity.ok(convertToDTO(reservaActualizada));
    }


    @DeleteMapping("/{id}/inactivar") // Borrado lógico por Admin
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> softDeleteReserva(@PathVariable Long id) {
        reservaService.findById(id).orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Reserva no encontrada con ID: " + id));
        reservaService.softDeleteById(id); // Cambia estado a INACTIVA
        return ResponseEntity.noContent().build();
    }
}

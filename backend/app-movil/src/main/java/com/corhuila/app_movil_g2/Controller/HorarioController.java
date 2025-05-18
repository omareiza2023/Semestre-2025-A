package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.HorarioCreateDTO;
import com.corhuila.app_movil_g2.Controller.dto.HorarioDTO;
import com.corhuila.app_movil_g2.Controller.dto.UsuarioDTO; // Reusado para anidar info de barbero
import com.corhuila.app_movil_g2.Controller.dto.PersonaDTO; // Reusado
import com.corhuila.app_movil_g2.Controller.dto.RolDTO; // Reusado
import com.corhuila.app_movil_g2.Models.Horario;
import com.corhuila.app_movil_g2.Models.Usuario;
import com.corhuila.app_movil_g2.Services.IHorarioService;
import com.corhuila.app_movil_g2.Services.IUsuarioService;
import com.corhuila.app_movil_g2.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private IHorarioService horarioService;

    @Autowired
    private IUsuarioService usuarioService;

    // --- Mappers ---
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

    private HorarioDTO convertToDTO(Horario horario) {
        if (horario == null) return null;
        return new HorarioDTO(
                horario.getId(),
                convertUsuarioToDTO(horario.getUsuario()), // Barbero
                horario.getDiasSemana(),
                horario.getHoraInicio(),
                horario.getHoraFin(),
                horario.getEstado()
        );
    }
    // --- Fin Mappers ---

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'BARBERO')")
    public ResponseEntity<HorarioDTO> createHorario(@Valid @RequestBody HorarioCreateDTO createDTO) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Usuario currentUser = (Usuario) authentication.getPrincipal();

    // Usa una nueva variable final
    final Long resolvedBarberoId;
    if (currentUser.getRol().getNombreRol().equals("BARBERO")) {
        resolvedBarberoId = currentUser.getId(); // Barbero solo puede crear para sí mismo
    } else if (createDTO.getIdUsuarioBarbero() == null) {
        throw new GlobalExceptionHandler.BusinessLogicException("Admin debe especificar el ID del barbero para el horario.");
    } else {
        resolvedBarberoId = createDTO.getIdUsuarioBarbero(); // Admin especifica
    }

    // Usa resolvedBarberoId que es final
    Usuario barbero = usuarioService.findByIdActive(resolvedBarberoId)
        .filter(u -> u.getRol().getNombreRol().equals("BARBERO"))
        .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Barbero activo no encontrado con ID: " + resolvedBarberoId));

    Horario nuevoHorario = new Horario();
    nuevoHorario.setUsuario(barbero);
    nuevoHorario.setDiasSemana(createDTO.getDiasSemana());
    nuevoHorario.setHoraInicio(createDTO.getHoraInicio());
    nuevoHorario.setHoraFin(createDTO.getHoraFin());

    Horario horarioGuardado = horarioService.save(nuevoHorario);
    return new ResponseEntity<>(convertToDTO(horarioGuardado), HttpStatus.CREATED);
    }

    @GetMapping("/barbero/{barberoId}/activos")
    @PreAuthorize("isAuthenticated()") // Todos pueden ver horarios de un barbero específico
    public ResponseEntity<List<HorarioDTO>> getHorariosActivosByBarbero(@PathVariable Long barberoId) {
        usuarioService.findByIdActive(barberoId)
            .filter(u -> u.getRol().getNombreRol().equals("BARBERO"))
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Barbero activo no encontrado con ID: " + barberoId));
        
        List<HorarioDTO> horarios = horarioService.findByBarberoIdActive(barberoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(horarios);
    }
    
    @GetMapping("/mis-horarios/activos")
    @PreAuthorize("hasRole('BARBERO')")
    public ResponseEntity<List<HorarioDTO>> getMisHorariosActivos() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Usuario currentUser = (Usuario) authentication.getPrincipal();
        
        List<HorarioDTO> horarios = horarioService.findByBarberoIdActive(currentUser.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(horarios);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HorarioDTO> getHorarioById(@PathVariable Long id) {
        Horario horario = horarioService.findById(id)
        .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Horario no encontrado con ID: " + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Usuario currentUser = (Usuario) authentication.getPrincipal();
        final String nombreRol = currentUser.getRol().getNombreRol();  // Extrae antes
        final boolean isAdmin = "ADMIN".equals(nombreRol);             // Comparación segura
        // Esto evita la evaluación ambigua del compilador
       if (!"ADMIN".equals(currentUser.getRol().getNombreRol())
    && !AppConstants.ESTADO_ACTIVO_HORARIO.equals(horario.getEstado())) {
    throw new GlobalExceptionHandler.ResourceNotFoundException("Horario no encontrado o inactivo con ID: " + id);
}
        return ResponseEntity.ok(convertToDTO(horario));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and @horarioServiceImpl.findById(#id).orElse(null)?.usuario.id == principal.id)")
    public ResponseEntity<HorarioDTO> updateHorario(@PathVariable Long id, @Valid @RequestBody HorarioCreateDTO updateDTO) {
        Horario horarioExistente = horarioService.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Horario no encontrado con ID: " + id));

        // El ID del barbero no debería cambiar en una actualización de horario.
        // Si se quiere reasignar, se debería borrar y crear uno nuevo.
        // Aquí solo actualizamos los campos del horario.
        horarioExistente.setDiasSemana(updateDTO.getDiasSemana());
        horarioExistente.setHoraInicio(updateDTO.getHoraInicio());
        horarioExistente.setHoraFin(updateDTO.getHoraFin());
        // El estado podría ser parte del DTO si se permite cambiarlo aquí
        
        Horario horarioActualizado = horarioService.save(horarioExistente);
        return ResponseEntity.ok(convertToDTO(horarioActualizado));
    }

    @PatchMapping("/{id}/inactivar")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and @horarioServiceImpl.findById(#id).orElse(null)?.usuario.id == principal.id)")
    public ResponseEntity<Void> softDeleteHorario(@PathVariable Long id) {
        horarioService.findByIdActive(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Horario activo no encontrado con ID: " + id));
        horarioService.softDeleteById(id); // Cambia estado a INACTIVO
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/activar")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('BARBERO') and @horarioServiceImpl.findById(#id).orElse(null)?.usuario.id == principal.id)")
    public ResponseEntity<HorarioDTO> activateHorario(@PathVariable Long id) {
        Horario horario = horarioService.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Horario no encontrado con ID: " + id));
        if (horario.getEstado().equals(AppConstants.ESTADO_ACTIVO_HORARIO)) {
            throw new GlobalExceptionHandler.BusinessLogicException("El horario ya está activo.");
        }
        horario.setEstado(AppConstants.ESTADO_ACTIVO_HORARIO);
        return ResponseEntity.ok(convertToDTO(horarioService.save(horario)));
    }
}


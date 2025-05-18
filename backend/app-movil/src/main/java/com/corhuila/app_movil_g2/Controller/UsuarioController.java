package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.*;
import com.corhuila.app_movil_g2.Controller.dto.auth.PasswordRecoveryRequestDTO; // Reusado para cambio de contraseña por admin
import com.corhuila.app_movil_g2.Models.Persona;
import com.corhuila.app_movil_g2.Models.Rol;
import com.corhuila.app_movil_g2.Models.Usuario;
import com.corhuila.app_movil_g2.Services.IPersonaService;
import com.corhuila.app_movil_g2.Services.IRolService;
import com.corhuila.app_movil_g2.Services.IUsuarioService;
import com.corhuila.app_movil_g2.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IPersonaService personaService; // Necesario si Persona se maneja separadamente

    @Autowired
    private IRolService rolService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- Mappers (Idealmente usar MapStruct o una clase Mapper dedicada) ---
    private PersonaDTO convertPersonaToDTO(Persona persona) {
        if (persona == null) return null;
        return new PersonaDTO(persona.getId(), persona.getNombre(), persona.getApellido(), persona.getDocumento(), persona.getTelefono());
    }

    private Persona convertPersonaDTOToEntity(PersonaDTO dto) {
        if (dto == null) return null;
        Persona persona = new Persona();
        persona.setId(dto.getId());
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setDocumento(dto.getDocumento());
        persona.setTelefono(dto.getTelefono());
        return persona;
    }

    private RolDTO convertRolToDTO(Rol rol) {
        if (rol == null) return null;
        return new RolDTO(rol.getId(), rol.getNombreRol());
    }

    private UsuarioDTO convertUsuarioToDTO(Usuario usuario) {
        if (usuario == null) return null;
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getUsername(),
                convertPersonaToDTO(usuario.getPersona()),
                convertRolToDTO(usuario.getRol()),
                usuario.isActivo()
        );
    }
    // --- Fin Mappers ---

    @PostMapping("/registrar/cliente") // Público según SecurityConfig
    public ResponseEntity<UsuarioDTO> registerCliente(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        if (usuarioService.existsByUsername(usuarioCreateDTO.getUsername())) {
            throw new GlobalExceptionHandler.BusinessLogicException("El nombre de usuario '" + usuarioCreateDTO.getUsername() + "' ya está en uso.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(usuarioCreateDTO.getUsername());
        nuevoUsuario.setPassword(usuarioCreateDTO.getPassword()); // El servicio se encarga de hashear

        // Manejo de Persona
        Persona persona = convertPersonaDTOToEntity(usuarioCreateDTO.getPersona());
        // Aquí podrías buscar si la persona ya existe por documento, si la lógica lo requiere
        // Por ahora, asumimos que siempre se crea una nueva persona o se actualiza si tiene ID
        if (persona.getId() != null) {
            Persona personaExistente = personaService.findById(persona.getId())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Persona no encontrada con ID: " + persona.getId()));
            // Actualizar campos si es necesario
            personaExistente.setNombre(persona.getNombre());
            personaExistente.setApellido(persona.getApellido());
            personaExistente.setDocumento(persona.getDocumento());
            personaExistente.setTelefono(persona.getTelefono());
            nuevoUsuario.setPersona(personaService.save(personaExistente));
        } else {
            nuevoUsuario.setPersona(personaService.save(persona));
        }


        // Asignar Rol Cliente por defecto
        Rol rolCliente = rolService.findByNombreRol(AppConstants.ROLE_CLIENTE.replace("ROLE_", ""))
                .orElseThrow(() -> new GlobalExceptionHandler.BusinessLogicException("Rol CLIENTE no encontrado. Configure los roles iniciales."));
        nuevoUsuario.setRol(rolCliente);

        Usuario usuarioRegistrado = usuarioService.registerUser(nuevoUsuario); // registerUser hashea la contraseña y guarda
        return new ResponseEntity<>(convertUsuarioToDTO(usuarioRegistrado), HttpStatus.CREATED);
    }

    @PostMapping("/registrar/barbero")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioDTO> registerBarbero(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        if (usuarioService.existsByUsername(usuarioCreateDTO.getUsername())) {
            throw new GlobalExceptionHandler.BusinessLogicException("El nombre de usuario '" + usuarioCreateDTO.getUsername() + "' ya está en uso.");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(usuarioCreateDTO.getUsername());
        nuevoUsuario.setPassword(usuarioCreateDTO.getPassword());

        Persona persona = convertPersonaDTOToEntity(usuarioCreateDTO.getPersona());
         if (persona.getId() != null) {
            Persona personaExistente = personaService.findById(persona.getId())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Persona no encontrada con ID: " + persona.getId()));
            personaExistente.setNombre(persona.getNombre());
            personaExistente.setApellido(persona.getApellido());
            personaExistente.setDocumento(persona.getDocumento());
            personaExistente.setTelefono(persona.getTelefono());
            nuevoUsuario.setPersona(personaService.save(personaExistente));
        } else {
            nuevoUsuario.setPersona(personaService.save(persona));
        }

        Rol rolBarbero = rolService.findByNombreRol(AppConstants.ROLE_BARBERO.replace("ROLE_", ""))
                .orElseThrow(() -> new GlobalExceptionHandler.BusinessLogicException("Rol BARBERO no encontrado."));
        nuevoUsuario.setRol(rolBarbero);

        Usuario usuarioRegistrado = usuarioService.registerUser(nuevoUsuario);
        return new ResponseEntity<>(convertUsuarioToDTO(usuarioRegistrado), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios(@RequestParam(required = false, defaultValue = "true") boolean activos) {
        List<Usuario> usuarios;
        if (activos) {
            usuarios = usuarioService.findAllActive();
        } else {
            usuarios = usuarioService.findAll(); // Incluye inactivos
        }
        return ResponseEntity.ok(usuarios.stream().map(this::convertUsuarioToDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('CLIENTE', 'BARBERO') and #id == principal.id)") // Admin puede ver cualquiera, otros solo a sí mismos
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        return usuarioService.findById(id) // Devuelve activo o inactivo, el DTO lo indicará
                .map(this::convertUsuarioToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasAnyRole('CLIENTE', 'BARBERO') and #id == principal.id)")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuarioExistente = usuarioService.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Usuario no encontrado con ID: " + id));

        // Actualizar Persona si se provee
        if (usuarioUpdateDTO.getPersona() != null) {
            Persona personaActualizada = convertPersonaDTOToEntity(usuarioUpdateDTO.getPersona());
            Persona personaAGuardar = usuarioExistente.getPersona();

            personaAGuardar.setNombre(personaActualizada.getNombre());
            personaAGuardar.setApellido(personaActualizada.getApellido());
            // Solo actualizar documento y teléfono si son provistos y diferentes (y válidos)
            if (personaActualizada.getDocumento() != null && !personaActualizada.getDocumento().equals(personaAGuardar.getDocumento())) {
                personaAGuardar.setDocumento(personaActualizada.getDocumento());
            }
            if (personaActualizada.getTelefono() != null && !personaActualizada.getTelefono().equals(personaAGuardar.getTelefono())) {
                personaAGuardar.setTelefono(personaActualizada.getTelefono());
            }
            usuarioExistente.setPersona(personaService.save(personaAGuardar));
        }

        // Actualizar contraseña si se provee
        if (usuarioUpdateDTO.getPassword() != null && !usuarioUpdateDTO.getPassword().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(usuarioUpdateDTO.getPassword()));
        }

        // Actualizar Rol (solo ADMIN)
        if (usuarioUpdateDTO.getIdRol() != null && SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(AppConstants.ROLE_ADMIN))) {
            Rol nuevoRol = rolService.findById(usuarioUpdateDTO.getIdRol())
                    .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Rol no encontrado con ID: " + usuarioUpdateDTO.getIdRol()));
            usuarioExistente.setRol(nuevoRol);
        }
        
        // Actualizar estado activo (solo ADMIN)
        if (usuarioUpdateDTO.getActivo() != null && SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(AppConstants.ROLE_ADMIN))) {
            usuarioExistente.setActivo(usuarioUpdateDTO.getActivo());
        }


        Usuario usuarioActualizado = usuarioService.save(usuarioExistente); // save del servicio no re-hashea si la contraseña no cambió
        return ResponseEntity.ok(convertUsuarioToDTO(usuarioActualizado));
    }

    @PatchMapping("/{id}/inactivar") // Usar PATCH para cambios parciales como estado
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> softDeleteUsuario(@PathVariable Long id) {
        usuarioService.findByIdActive(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Usuario activo no encontrado con ID: " + id));
        usuarioService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/activar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> activateUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Usuario no encontrado con ID: " + id));
        if (usuario.isActivo()) {
            throw new GlobalExceptionHandler.BusinessLogicException("El usuario ya está activo.");
        }
        usuario.setActivo(true);
        usuarioService.save(usuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/barberos/activos")
    @PreAuthorize("isAuthenticated()") // Clientes o Admin pueden ver barberos
    public ResponseEntity<List<UsuarioDTO>> getBarberosActivos() {
        List<UsuarioDTO> barberos = usuarioService.findBarberosActive().stream()
                .map(this::convertUsuarioToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(barberos);
    }

    @GetMapping("/clientes/activos")
    @PreAuthorize("hasAnyRole('ADMIN', 'BARBERO')") // Solo Admin o Barbero pueden ver lista de clientes
    public ResponseEntity<List<UsuarioDTO>> getClientesActivos() {
        List<UsuarioDTO> clientes = usuarioService.findClientesActive().stream()
                .map(this::convertUsuarioToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }
}

package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.RolDTO;
import com.corhuila.app_movil_g2.Models.Rol;
import com.corhuila.app_movil_g2.Services.IRolService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")
@PreAuthorize("hasRole('ADMIN')") // Solo ADMIN puede gestionar roles
public class RolController {

    @Autowired
    private IRolService rolService;

    // Mapeador simple (idealmente usar MapStruct o similar)
    private RolDTO convertToDTO(Rol rol) {
        return new RolDTO(rol.getId(), rol.getNombreRol());
    }

    private Rol convertToEntity(RolDTO rolDTO) {
        Rol rol = new Rol();
        rol.setId(rolDTO.getId());
        rol.setNombreRol(rolDTO.getNombreRol());
        return rol;
    }

    @PostMapping
    public ResponseEntity<RolDTO> createRol(@Valid @RequestBody RolDTO rolDTO) {
        Rol rol = convertToEntity(rolDTO);
        Rol nuevoRol = rolService.save(rol);
        return new ResponseEntity<>(convertToDTO(nuevoRol), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RolDTO>> getAllRoles() {
        List<RolDTO> roles = rolService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> getRolById(@PathVariable Long id) {
        return rolService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Rol no encontrado con ID: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> updateRol(@PathVariable Long id, @Valid @RequestBody RolDTO rolDTO) {
        return rolService.findById(id)
                .map(rolExistente -> {
                    rolExistente.setNombreRol(rolDTO.getNombreRol());
                    Rol rolActualizado = rolService.save(rolExistente);
                    return ResponseEntity.ok(convertToDTO(rolActualizado));
                })
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Rol no encontrado con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRol(@PathVariable Long id) {
        if (!rolService.findById(id).isPresent()) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Rol no encontrado con ID: " + id);
        }
        rolService.deleteById(id); // Borrado físico
        return ResponseEntity.noContent().build();
    }
}

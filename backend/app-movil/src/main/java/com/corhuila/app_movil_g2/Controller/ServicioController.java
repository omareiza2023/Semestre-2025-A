package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.CategoriaServicioDTO;
import com.corhuila.app_movil_g2.Controller.dto.ServicioCreateDTO;
import com.corhuila.app_movil_g2.Controller.dto.ServicioDTO;
import com.corhuila.app_movil_g2.Models.CategoriaServicio;
import com.corhuila.app_movil_g2.Models.Servicio;
import com.corhuila.app_movil_g2.Services.ICategoriaServicioService;
import com.corhuila.app_movil_g2.Services.IServicioService;
import com.corhuila.app_movil_g2.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    @Autowired
    private ICategoriaServicioService categoriaServicioService; // Para obtener la categoría al crear/actualizar

    // --- Mappers ---
    private CategoriaServicioDTO convertCategoriaToDTO(CategoriaServicio categoria) {
        if (categoria == null) return null;
        return new CategoriaServicioDTO(categoria.getId(), categoria.getNombre(), categoria.getPrecio(), categoria.isActivo());
    }
    
    private ServicioDTO convertToDTO(Servicio servicio) {
        if (servicio == null) return null;
        return new ServicioDTO(
                servicio.getId(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                convertCategoriaToDTO(servicio.getCategoriaServicio()),
                servicio.isActivo()
        );
    }

    private Servicio convertToEntity(ServicioCreateDTO createDTO, CategoriaServicio categoria) {
        Servicio servicio = new Servicio();
        servicio.setNombre(createDTO.getNombre());
        servicio.setDescripcion(createDTO.getDescripcion());
        servicio.setCategoriaServicio(categoria);
        // activo se setea por defecto en la entidad o servicio
        return servicio;
    }
    // --- Fin Mappers ---

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServicioDTO> createServicio(@Valid @RequestBody ServicioCreateDTO createDTO) {
        CategoriaServicio categoria = categoriaServicioService.findByIdActive(createDTO.getIdCategoriaServicio())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Categoría de servicio activa no encontrada con ID: " + createDTO.getIdCategoriaServicio()));
        
        Servicio servicio = convertToEntity(createDTO, categoria);
        Servicio nuevoServicio = servicioService.save(servicio);
        return new ResponseEntity<>(convertToDTO(nuevoServicio), HttpStatus.CREATED);
    }

    @GetMapping("/activos") // Público o autenticado según SecurityConfig
    public ResponseEntity<List<ServicioDTO>> getAllServiciosActivos() {
        List<ServicioDTO> servicios = servicioService.findAllActive().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(servicios);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ServicioDTO>> getAllServicios() {
        List<ServicioDTO> servicios = servicioService.findAll().stream() // Incluye inactivos
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ServicioDTO> getServicioById(@PathVariable Long id) {
         Servicio servicio = servicioService.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Servicio no encontrado con ID: " + id));

        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals(AppConstants.ROLE_ADMIN));
        if (!isAdmin && !servicio.isActivo()) {
             throw new GlobalExceptionHandler.ResourceNotFoundException("Servicio no encontrado o no activo con ID: " + id);
        }
        return ResponseEntity.ok(convertToDTO(servicio));
    }

    @GetMapping("/categoria/{categoriaId}/activos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ServicioDTO>> getServiciosActivosByCategoria(@PathVariable Long categoriaId) {
        // Verificar que la categoría exista y esté activa
        categoriaServicioService.findByIdActive(categoriaId)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Categoría de servicio activa no encontrada con ID: " + categoriaId));
        
        List<ServicioDTO> servicios = servicioService.findByCategoriaIdActive(categoriaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(servicios);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServicioDTO> updateServicio(@PathVariable Long id, @Valid @RequestBody ServicioCreateDTO servicioUpdateDTO) { // Reusamos CreateDTO para campos
        Servicio servicioExistente = servicioService.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Servicio no encontrado con ID: " + id));

        CategoriaServicio categoria = categoriaServicioService.findByIdActive(servicioUpdateDTO.getIdCategoriaServicio())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Categoría de servicio activa no encontrada con ID: " + servicioUpdateDTO.getIdCategoriaServicio()));

        servicioExistente.setNombre(servicioUpdateDTO.getNombre());
        servicioExistente.setDescripcion(servicioUpdateDTO.getDescripcion());
        servicioExistente.setCategoriaServicio(categoria);
        // servicioExistente.setActivo(dto.isActivo()); // Si se permite cambiar aquí

        Servicio servicioActualizado = servicioService.save(servicioExistente);
        return ResponseEntity.ok(convertToDTO(servicioActualizado));
    }

    @PatchMapping("/{id}/inactivar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> softDeleteServicio(@PathVariable Long id) {
        servicioService.findByIdActive(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Servicio activo no encontrado con ID: " + id));
        servicioService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/activar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServicioDTO> activateServicio(@PathVariable Long id) {
        Servicio servicio = servicioService.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Servicio no encontrado con ID: " + id));
        if (servicio.isActivo()) {
            throw new GlobalExceptionHandler.BusinessLogicException("El servicio ya está activo.");
        }
        servicio.setActivo(true);
        return ResponseEntity.ok(convertToDTO(servicioService.save(servicio)));
    }
}

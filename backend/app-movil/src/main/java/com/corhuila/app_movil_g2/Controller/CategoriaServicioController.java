package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.CategoriaServicioCreateDTO;
import com.corhuila.app_movil_g2.Controller.dto.CategoriaServicioDTO;
import com.corhuila.app_movil_g2.Models.CategoriaServicio;
import com.corhuila.app_movil_g2.Services.ICategoriaServicioService;
import com.corhuila.app_movil_g2.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categoriaservicios")
public class CategoriaServicioController {

    @Autowired
    private ICategoriaServicioService categoriaServicioService;

    // --- Mappers ---
    private CategoriaServicioDTO convertToDTO(CategoriaServicio categoria) {
        if (categoria == null) return null;
        return new CategoriaServicioDTO(categoria.getId(), categoria.getNombre(), categoria.getPrecio(), categoria.isActivo());
    }

    private CategoriaServicio convertToEntity(CategoriaServicioCreateDTO dto) {
        CategoriaServicio categoria = new CategoriaServicio();
        categoria.setNombre(dto.getNombre());
        categoria.setPrecio(dto.getPrecio());
        // activo se setea por defecto en la entidad o servicio
        return categoria;
    }
    // --- Fin Mappers ---

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaServicioDTO> createCategoriaServicio(@Valid @RequestBody CategoriaServicioCreateDTO createDTO) {
        CategoriaServicio categoria = convertToEntity(createDTO);
        CategoriaServicio nuevaCategoria = categoriaServicioService.save(categoria);
        return new ResponseEntity<>(convertToDTO(nuevaCategoria), HttpStatus.CREATED);
    }

    @GetMapping("/activos") // Público o autenticado según SecurityConfig
    public ResponseEntity<List<CategoriaServicioDTO>> getAllCategoriasServicioActivas() {
        List<CategoriaServicioDTO> categorias = categoriaServicioService.findAllActive().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoriaServicioDTO>> getAllCategoriasServicio() {
        List<CategoriaServicioDTO> categorias = categoriaServicioService.findAll().stream() // Incluye inactivas
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Todos los autenticados pueden ver si es activa, admin siempre
    public ResponseEntity<CategoriaServicioDTO> getCategoriaServicioById(@PathVariable Long id) {
        CategoriaServicio categoria = categoriaServicioService.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Categoría de servicio no encontrada con ID: " + id));

        // Si no es admin y la categoría no está activa, no permitir verla (o filtrar en el servicio)
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                            .anyMatch(a -> a.getAuthority().equals(AppConstants.ROLE_ADMIN));
        if (!isAdmin && !categoria.isActivo()) {
             throw new GlobalExceptionHandler.ResourceNotFoundException("Categoría de servicio no encontrada o no activa con ID: " + id);
        }
        return ResponseEntity.ok(convertToDTO(categoria));
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaServicioDTO> updateCategoriaServicio(@PathVariable Long id, @Valid @RequestBody CategoriaServicioDTO categoriaDTO) {
        return categoriaServicioService.findById(id) // Busca por ID sin importar estado para actualizar
                .map(categoriaExistente -> {
                    categoriaExistente.setNombre(categoriaDTO.getNombre());
                    categoriaExistente.setPrecio(categoriaDTO.getPrecio());
                    // El admin puede decidir si la reactiva o la mantiene con su estado actual si el DTO lo trae
                    // categoriaExistente.setActivo(categoriaDTO.isActivo()); // Si se permite cambiar estado activo aquí
                    CategoriaServicio categoriaActualizada = categoriaServicioService.save(categoriaExistente);
                    return ResponseEntity.ok(convertToDTO(categoriaActualizada));
                })
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Categoría de servicio no encontrada con ID: " + id));
    }

    @PatchMapping("/{id}/inactivar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> softDeleteCategoriaServicio(@PathVariable Long id) {
        categoriaServicioService.findByIdActive(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Categoría de servicio activa no encontrada con ID: " + id));
        categoriaServicioService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/activar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaServicioDTO> activateCategoriaServicio(@PathVariable Long id) {
        CategoriaServicio categoria = categoriaServicioService.findById(id)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Categoría de servicio no encontrada con ID: " + id));
        if (categoria.isActivo()) {
            throw new GlobalExceptionHandler.BusinessLogicException("La categoría de servicio ya está activa.");
        }
        categoria.setActivo(true);
        return ResponseEntity.ok(convertToDTO(categoriaServicioService.save(categoria)));
    }
}

package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.*;
import com.corhuila.app_movil_g2.Models.*;
import com.corhuila.app_movil_g2.Services.*;
import com.corhuila.app_movil_g2.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;
    @Autowired
    private IUsuarioService usuarioService;
    @Autowired
    private IReservaService reservaService;
    @Autowired
    private IPagoService pagoService;
    @Autowired
    private IDetalleFacturaService detalleFacturaService; // Para guardar los detalles


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
    
    private DetalleFacturaDTO convertDetalleToDTO(DetalleFactura detalle) {
        if (detalle == null) return null;
        DetalleFacturaDTO dto = new DetalleFacturaDTO();
        dto.setId(detalle.getId());
        if (detalle.getReserva() != null) {
            dto.setIdReserva(detalle.getReserva().getId());
            if (detalle.getReserva().getServicio() != null) {
                dto.setNombreServicio(detalle.getReserva().getServicio().getNombre());
                if (detalle.getReserva().getServicio().getCategoriaServicio() != null) {
                     dto.setPrecioServicio(detalle.getReserva().getServicio().getCategoriaServicio().getPrecio());
                }
            }
        }
        if (detalle.getFactura() != null) dto.setIdFactura(detalle.getFactura().getId());
        if (detalle.getPago() != null) dto.setIdPago(detalle.getPago().getId());
        return dto;
    }

    private FacturaDTO convertToDTO(Factura factura) {
        if (factura == null) return null;
        List<DetalleFacturaDTO> detallesDTO = factura.getDetallesFactura() != null ?
                factura.getDetallesFactura().stream().map(this::convertDetalleToDTO).collect(Collectors.toList())
                : new ArrayList<>();
        return new FacturaDTO(
                factura.getId(),
                convertUsuarioToDTO(factura.getUsuario()),
                factura.getFecha(),
                detallesDTO
        );
    }
    // --- Fin Mappers ---

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // O un sistema automático después de completar una reserva
    @Transactional // Importante para manejar múltiples guardados (Factura, DetalleFactura, Pago)
    public ResponseEntity<FacturaDTO> createFactura(@Valid @RequestBody FacturaCreateDTO createDTO) {
        Usuario cliente = usuarioService.findByIdActive(createDTO.getIdUsuarioCliente())
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Cliente activo no encontrado con ID: " + createDTO.getIdUsuarioCliente()));

        Factura nuevaFactura = new Factura();
        nuevaFactura.setUsuario(cliente);
        nuevaFactura.setFecha(LocalDateTime.now());
        
        // Guardar la factura primero para obtener su ID
        Factura facturaGuardada = facturaService.save(nuevaFactura);

        List<DetalleFactura> detallesAGuardar = new ArrayList<>();
        Pago pagoAsociado = null;

        // Procesar pago si se incluye
        if (createDTO.getPagoInfo() != null) {
            Pago pagoParaGuardar = new Pago();
            pagoParaGuardar.setTipoPago(createDTO.getPagoInfo().getTipoPago());
            pagoParaGuardar.setMonto(createDTO.getPagoInfo().getMonto());
            pagoParaGuardar.setFechaPago(createDTO.getPagoInfo().getFechaPago() != null ? createDTO.getPagoInfo().getFechaPago() : LocalDateTime.now());
            pagoAsociado = pagoService.save(pagoParaGuardar);
        }

        if (pagoAsociado == null) { // Si no hay pago, no se puede crear factura (o manejar lógica diferente)
            throw new GlobalExceptionHandler.BusinessLogicException("Se requiere información de pago para generar la factura.");
        }

        for (Long idReserva : createDTO.getIdsReservasAFacturar()) {
            Reserva reserva = reservaService.findById(idReserva)
                    .filter(r -> r.getEstado().equals(AppConstants.ESTADO_COMPLETADA) || r.getEstado().equals(AppConstants.ESTADO_ACTIVA)) // Solo facturar completadas o activas (según lógica)
                    .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Reserva válida no encontrada con ID: " + idReserva));

            DetalleFactura detalle = new DetalleFactura();
            detalle.setFactura(facturaGuardada);
            detalle.setReserva(reserva);
            detalle.setPago(pagoAsociado); // Todos los detalles de esta factura comparten el mismo pago
            detallesAGuardar.add(detalleFacturaService.save(detalle)); // Guardar cada detalle
        }
        
        if (detallesAGuardar.isEmpty()) {
            // Rollback o manejo: si no hay detalles, ¿la factura es válida?
            // Podrías borrar la facturaGuardada y el pagoAsociado si la transacción no se maneja automáticamente.
            // @Transactional debería encargarse del rollback si se lanza una excepción aquí.
            throw new GlobalExceptionHandler.BusinessLogicException("No se pudieron generar detalles para la factura. Ninguna reserva válida proporcionada.");
        }

        facturaGuardada.setDetallesFactura(detallesAGuardar);
        // Volver a guardar factura si la relación de detalles no se actualiza automáticamente (depende de la config JPA y Cascade)
        // Factura facturaCompleta = facturaService.save(facturaGuardada); // Opcional
        
        // Recargar la factura para obtener todos los detalles
        Factura facturaCompleta = facturaService.findById(facturaGuardada.getId()).get();

        return new ResponseEntity<>(convertToDTO(facturaCompleta), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<FacturaDTO>> getAllFacturas() {
        List<FacturaDTO> facturas = facturaService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and #usuarioId == principal.id)")
    public ResponseEntity<List<FacturaDTO>> getFacturasByUsuario(@PathVariable Long usuarioId) {
        usuarioService.findByIdActive(usuarioId) // Valida que el usuario exista
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Usuario activo no encontrado con ID: " + usuarioId));

        List<FacturaDTO> facturas = facturaService.findByUsuarioId(usuarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(facturas);
    }
    
    @GetMapping("/mis-facturas")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<List<FacturaDTO>> getMisFacturas() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Usuario currentUser = (Usuario) authentication.getPrincipal();

        List<FacturaDTO> facturas = facturaService.findByUsuarioId(currentUser.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(facturas);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()") // Lógica más fina dentro del método
    public ResponseEntity<FacturaDTO> getFacturaById(@PathVariable Long id) {
        Factura factura = facturaService.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Factura no encontrada con ID: " + id));
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Usuario currentUser = (Usuario) authentication.getPrincipal();

        if (!currentUser.getRol().getNombreRol().equals("ADMIN") && !factura.getUsuario().getId().equals(currentUser.getId())) {
             throw new GlobalExceptionHandler.BusinessLogicException("No tiene permiso para ver esta factura.");
        }
        return ResponseEntity.ok(convertToDTO(factura));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteFactura(@PathVariable Long id) {
        Factura factura = facturaService.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Factura no encontrada con ID: " + id));
        
        // Considerar borrado en cascada de DetallesFactura (orphanRemoval=true en la entidad Factura)
        // O borrar manualmente los detalles primero si no hay cascada.
        // Los Pagos podrían o no borrarse dependiendo de la lógica de negocio.
        facturaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
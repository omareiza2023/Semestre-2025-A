package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.DetalleFacturaDTO;
import com.corhuila.app_movil_g2.Models.DetalleFactura;
import com.corhuila.app_movil_g2.Models.Factura;
import com.corhuila.app_movil_g2.Models.Pago;
import com.corhuila.app_movil_g2.Models.Reserva;
import com.corhuila.app_movil_g2.Services.IDetalleFacturaService;
import com.corhuila.app_movil_g2.Services.IFacturaService;
import com.corhuila.app_movil_g2.Services.IPagoService;
import com.corhuila.app_movil_g2.Services.IReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/detallefacturas")
@PreAuthorize("hasRole('ADMIN')") // Detalles de factura suelen ser para Admin
public class DetalleFacturaController {

    @Autowired
    private IDetalleFacturaService detalleFacturaService;
    @Autowired
    private IReservaService reservaService; // Para enriquecer DTO
    @Autowired
    private IFacturaService facturaService; // Para validar existencia
    @Autowired
    private IPagoService pagoService; // Para validar existencia


    // --- Mappers ---
    private DetalleFacturaDTO convertToDTO(DetalleFactura detalle) {
        if (detalle == null) return null;
        DetalleFacturaDTO dto = new DetalleFacturaDTO();
        dto.setId(detalle.getId());
        if (detalle.getReserva() != null) {
            dto.setIdReserva(detalle.getReserva().getId());
            // Enriquecer con datos de la reserva/servicio si es necesario
            if (detalle.getReserva().getServicio() != null) {
                dto.setNombreServicio(detalle.getReserva().getServicio().getNombre());
                if (detalle.getReserva().getServicio().getCategoriaServicio() != null) { // Asumiendo que servicio tiene precio en categoría
                    dto.setPrecioServicio(detalle.getReserva().getServicio().getCategoriaServicio().getPrecio());
                }
            }
        }
        if (detalle.getFactura() != null) dto.setIdFactura(detalle.getFactura().getId());
        if (detalle.getPago() != null) dto.setIdPago(detalle.getPago().getId());
        return dto;
    }
    // Nota: La creación de DetalleFactura usualmente ocurre al crear la Factura.
    // Un endpoint POST aquí sería si se añaden detalles a una factura existente, lo cual es menos común.
    // --- Fin Mappers ---

    @GetMapping("/factura/{facturaId}")
    public ResponseEntity<List<DetalleFacturaDTO>> getDetallesByFacturaId(@PathVariable Long facturaId) {
        facturaService.findById(facturaId) // Valida que la factura exista
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Factura no encontrada con ID: " + facturaId));

        List<DetalleFacturaDTO> detalles = detalleFacturaService.findByFacturaId(facturaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(detalles);
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<DetalleFacturaDTO>> getDetallesByReservaId(@PathVariable Long reservaId) {
         reservaService.findById(reservaId) // Valida que la reserva exista
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Reserva no encontrada con ID: " + reservaId));

        List<DetalleFacturaDTO> detalles = detalleFacturaService.findByReservaId(reservaId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(detalles);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DetalleFacturaDTO> getDetalleFacturaById(@PathVariable Long id) {
        return detalleFacturaService.findById(id)
            .map(this::convertToDTO)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Detalle de factura no encontrado con ID: " + id));
    }
}

package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.PagoCreateDTO;
import com.corhuila.app_movil_g2.Controller.dto.PagoDTO;
import com.corhuila.app_movil_g2.Models.Pago;
import com.corhuila.app_movil_g2.Services.IPagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagos")
@PreAuthorize("hasRole('ADMIN')") // Generalmente, los pagos los gestiona un Admin o sistema
public class PagoController {

    @Autowired
    private IPagoService pagoService;

    // --- Mappers ---
    private PagoDTO convertToDTO(Pago pago) {
        if (pago == null) return null;
        return new PagoDTO(pago.getId(), pago.getTipoPago(), pago.getMonto(), pago.getFechaPago());
    }

    private Pago convertToEntity(PagoCreateDTO createDTO) {
        Pago pago = new Pago();
        pago.setTipoPago(createDTO.getTipoPago());
        pago.setMonto(createDTO.getMonto());
        pago.setFechaPago(createDTO.getFechaPago());
        return pago;
    }
    // --- Fin Mappers ---

    @PostMapping
    public ResponseEntity<PagoDTO> createPago(@Valid @RequestBody PagoCreateDTO createDTO) {
        Pago pago = convertToEntity(createDTO);
        // Aquí podría haber lógica para asociar el pago a una factura o reserva
        Pago nuevoPago = pagoService.save(pago);
        return new ResponseEntity<>(convertToDTO(nuevoPago), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PagoDTO>> getAllPagos() {
        List<PagoDTO> pagos = pagoService.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> getPagoById(@PathVariable Long id) {
        return pagoService.findById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Pago no encontrado con ID: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> updatePago(@PathVariable Long id, @Valid @RequestBody PagoDTO pagoDTO) {
        return pagoService.findById(id)
                .map(pagoExistente -> {
                    pagoExistente.setTipoPago(pagoDTO.getTipoPago());
                    pagoExistente.setMonto(pagoDTO.getMonto());
                    pagoExistente.setFechaPago(pagoDTO.getFechaPago());
                    Pago pagoActualizado = pagoService.save(pagoExistente);
                    return ResponseEntity.ok(convertToDTO(pagoActualizado));
                })
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Pago no encontrado con ID: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id) {
        if (!pagoService.findById(id).isPresent()) {
             throw new GlobalExceptionHandler.ResourceNotFoundException("Pago no encontrado con ID: " + id);
        }
        pagoService.deleteById(id); // Borrado físico
        return ResponseEntity.noContent().build();
    }
}

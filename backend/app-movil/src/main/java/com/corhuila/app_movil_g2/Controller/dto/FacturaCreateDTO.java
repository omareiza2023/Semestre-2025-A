package com.corhuila.app_movil_g2.Controller.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaCreateDTO {

    @NotNull(message = "El ID del usuario (cliente) es obligatorio")
    private Long idUsuarioCliente;

    // La fecha se puede generar automáticamente al crear
    // private LocalDateTime fecha;

    @NotEmpty(message = "La factura debe tener al menos un detalle de reserva/pago")
    // Lista de IDs de reservas que se van a facturar, o IDs de pagos si el pago es previo.
    // Esto dependerá de tu flujo de negocio.
    private List<Long> idsReservasAFacturar;

    // Opcionalmente, si el pago se registra al mismo tiempo que la factura
    private PagoCreateDTO pagoInfo;
}

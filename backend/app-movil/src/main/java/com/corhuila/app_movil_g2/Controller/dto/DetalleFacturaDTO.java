package com.corhuila.app_movil_g2.Controller.dto;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFacturaDTO {
    private Long id;
    private Long idReserva; // Solo ID para evitar ciclos si ReservaDTO tiene DetalleFacturaDTO
    private Long idFactura; // Solo ID
    private Long idPago;    // Solo ID
    // Podrías añadir más detalles de la reserva/servicio si es necesario aquí para la visualización
    private String nombreServicio;
    private BigDecimal precioServicio;
}

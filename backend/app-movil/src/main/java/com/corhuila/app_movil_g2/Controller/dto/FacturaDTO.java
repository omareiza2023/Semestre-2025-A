package com.corhuila.app_movil_g2.Controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FacturaDTO {
    private Long id;

    @NotNull
    private UsuarioDTO usuario; // Cliente

    @NotNull
    private LocalDateTime fecha;

    private List<DetalleFacturaDTO> detallesFactura; // Lista de DTOs de detalles
}

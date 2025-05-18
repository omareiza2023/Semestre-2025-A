package com.corhuila.app_movil_g2.Controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaUpdateEstadoDTO {
    @NotBlank(message = "El nuevo estado es obligatorio")
    private String nuevoEstado; // Ej: "CANCELADA", "COMPLETADA"
}

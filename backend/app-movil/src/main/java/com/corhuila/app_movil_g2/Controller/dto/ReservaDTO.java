package com.corhuila.app_movil_g2.Controller.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long id;

    @NotNull(message = "El usuario de la reserva es obligatorio")
    private UsuarioDTO usuario; // Quién hace la reserva (Cliente)

    // Si el barbero es una entidad separada y asignada:
    // private UsuarioDTO barberoAsignado;

    @NotNull(message = "El servicio de la reserva es obligatorio")
    private ServicioDTO servicio;

    @NotNull(message = "La fecha y hora de la reserva son obligatorias")
    @FutureOrPresent(message = "La fecha de la reserva debe ser en el presente o futuro")
    private LocalDateTime fecha;

    @NotBlank(message = "El estado de la reserva no puede estar vacío")
    private String estado; // Ej: "ACTIVA", "CANCELADA", "COMPLETADA"
}

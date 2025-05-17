package com.corhuila.app_movil_g2.Controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioCreateDTO {

    @NotNull(message = "El ID del usuario (barbero) es obligatorio")
    private Long idUsuarioBarbero;

    @NotBlank(message = "Los días de la semana no pueden estar vacíos")
    @Size(max = 100)
    private String diasSemana;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    // El estado se maneja internamente, por defecto "ACTIVO"
}

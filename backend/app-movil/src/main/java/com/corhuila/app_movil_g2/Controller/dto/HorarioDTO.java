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
public class HorarioDTO {
    private Long id;

    @NotNull(message = "El usuario (barbero) del horario es obligatorio")
    private UsuarioDTO usuario; // Barbero

    @NotBlank(message = "Los días de la semana no pueden estar vacíos")
    @Size(max = 100, message = "La cadena de días de semana es muy larga") // Ajustar según formato "LUNES,MARTES" o similar
    private String diasSemana; // Ej: "LUNES", "MARTES-VIERNES"

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    @NotBlank(message = "El estado del horario no puede estar vacío")
    private String estado; // Ej: "ACTIVO", "INACTIVO"
}

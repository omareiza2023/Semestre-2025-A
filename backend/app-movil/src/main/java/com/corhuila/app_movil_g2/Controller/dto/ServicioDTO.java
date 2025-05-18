package com.corhuila.app_movil_g2.Controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {
    private Long id;

    @NotBlank(message = "El nombre del servicio no puede estar vacío")
    @Size(max = 50, message = "El nombre del servicio no puede exceder los 50 caracteres")
    private String nombre;

    private String descripcion; // Puede ser nula

    @NotNull(message = "La categoría del servicio es obligatoria")
    private CategoriaServicioDTO categoriaServicio; // Anidar DTO

    private boolean activo;
}

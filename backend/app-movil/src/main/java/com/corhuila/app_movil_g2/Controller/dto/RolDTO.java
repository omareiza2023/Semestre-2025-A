package com.corhuila.app_movil_g2.Controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolDTO {
    private Long id;

    @NotBlank(message = "El nombre del rol no puede estar vacío")
    @Size(max = 20, message = "El nombre del rol no puede exceder los 20 caracteres")
    private String nombreRol;
}

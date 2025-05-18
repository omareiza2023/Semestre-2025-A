package com.corhuila.app_movil_g2.Controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String username;
    private PersonaDTO persona; // Anidar DTO de Persona
    private RolDTO rol;         // Anidar DTO de Rol
    private boolean activo;
}

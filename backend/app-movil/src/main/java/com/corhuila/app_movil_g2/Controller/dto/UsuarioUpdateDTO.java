package com.corhuila.app_movil_g2.Controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUpdateDTO {

    // Username no se suele permitir actualizar, o con restricciones
    // private String username;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres si se provee")
    private String password; // Opcional, solo si se quiere cambiar

    @Valid
    private PersonaDTO persona; // Opcional, solo si se quiere cambiar

    private Long idRol; // Opcional, solo si se quiere cambiar el rol (usualmente solo ADMIN)

    private Boolean activo; // Para activar/desactivar
}

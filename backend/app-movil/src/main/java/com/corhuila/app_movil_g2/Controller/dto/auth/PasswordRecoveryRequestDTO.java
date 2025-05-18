package com.corhuila.app_movil_g2.Controller.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRecoveryRequestDTO {

    @NotBlank(message = "El nombre de usuario o email no puede estar vacío")
    private String usernameOrEmail;
}

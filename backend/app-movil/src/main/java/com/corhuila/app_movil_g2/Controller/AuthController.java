package com.corhuila.app_movil_g2.Controller;

import com.corhuila.app_movil_g2.Controller.advice.GlobalExceptionHandler;
import com.corhuila.app_movil_g2.Controller.dto.auth.AuthResponseDTO;
import com.corhuila.app_movil_g2.Controller.dto.auth.LoginRequestDTO;
import com.corhuila.app_movil_g2.Controller.dto.auth.PasswordRecoveryRequestDTO;
import com.corhuila.app_movil_g2.Models.Usuario;
import com.corhuila.app_movil_g2.Services.impl.UsuarioServiceImpl; // Importa la implementación
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioServiceImpl usuarioService; // Usamos la implementación directamente para recuperarContraseña

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // El manejo de la sesión (cookie) lo hace Spring Security automáticamente aquí.
            // Si fuera JWT, aquí se generaría y devolvería el token.
            Usuario userDetails = (Usuario) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(Collectors.toList());

            return ResponseEntity.ok(new AuthResponseDTO(
                    userDetails.getUsername(),
                    "Usuario autenticado exitosamente!",
                    roles
            ));
        } catch (Exception e) {
             e.printStackTrace();
            // AuthenticationException será manejada por GlobalExceptionHandler si está configurado
            // pero puedes devolver una respuesta más específica si quieres.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(new GlobalExceptionHandler.ErrorResponse(
                                         java.time.LocalDateTime.now(),
                                         HttpStatus.UNAUTHORIZED.value(),
                                         "Error de autenticación",
                                         "Nombre de usuario o contraseña incorrectos.",
                                         "/api/auth/login"
                                 ));
        }
    }

    @PostMapping("/recuperar-contrasenia")
    public ResponseEntity<?> recoverPassword(@Valid @RequestBody PasswordRecoveryRequestDTO recoveryRequest) {
        try {
            String message = usuarioService.recuperarContraseña(recoveryRequest.getUsernameOrEmail());
            return ResponseEntity.ok().body(Map.of("message", message));
        } catch (MessagingException e) {
            // Loggear el error de correo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new GlobalExceptionHandler.ErrorResponse(
                                         java.time.LocalDateTime.now(),
                                         HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                         "Error de envío de correo",
                                         "No se pudo enviar el correo de recuperación: " + e.getMessage(),
                                         "/api/auth/recuperar-contrasenia"
                                 ));
        } catch (GlobalExceptionHandler.ResourceNotFoundException | org.springframework.security.core.userdetails.UsernameNotFoundException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new GlobalExceptionHandler.ErrorResponse(
                                         java.time.LocalDateTime.now(),
                                         HttpStatus.NOT_FOUND.value(),
                                         "Usuario no encontrado",
                                         e.getMessage(),
                                         "/api/auth/recuperar-contrasenia"
                                 ));
        }
    }
}

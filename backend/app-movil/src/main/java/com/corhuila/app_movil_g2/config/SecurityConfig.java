package com.corhuila.app_movil_g2.config;

import com.corhuila.app_movil_g2.Services.IUsuarioService; // Usamos nuestra implementación de UserDetailsService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Habilita la seguridad web de Spring Security
public class SecurityConfig {

    @Autowired
    private IUsuarioService usuarioService; // Nuestra implementación de UserDetailsService

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Usamos BCrypt para hashear contraseñas
    }

    @Bean
    public AuthenticationManager authenticationManager(IUsuarioService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para APIs REST sin estado (si usas sesiones, podrías necesitar habilitarlo)
            .authorizeHttpRequests(auth -> auth
                // Permite acceso público a la documentación de Swagger
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Permite acceso público al endpoint de registro de cliente y login
                .requestMatchers("/api/usuarios/registrar/cliente", "/api/auth/login", "/api/usuarios/recuperarcontrasenia").permitAll()
                 // Permite acceso público a listar servicios y categorías activas (si no requieren autenticación)
                .requestMatchers("/api/servicios/activos", "/api/categoriaservicios/activos").permitAll()
                // Proteger endpoints basándose en roles
                // Ejemplo: Solo ADMIN puede gestionar roles y usuarios (listar todos, soft delete)
                .requestMatchers("/api/roles/**", "/api/usuarios", "/api/usuarios/{id}", "/api/usuarios/inactivar/{id}").hasRole("ADMIN")
                // Ejemplo: ADMIN y BARBERO pueden crear usuarios (clientes o barberos por ADMIN, solo clientes por BARBERO?)
                // Esto es más complejo y se manejaría dentro de la lógica del controlador si BARBERO solo puede crear CLIENTES
                // Por ahora, permitiremos que ADMIN cree todos, y un endpoint específico para CLIENTE público.
                .requestMatchers("/api/usuarios/registrar/barbero").hasRole("ADMIN") // Permitir a ADMIN crear barberos
                 // Ejemplo: ADMIN y BARBERO pueden gestionar horarios
                .requestMatchers("/api/horarios/**").hasAnyRole("ADMIN", "BARBERO")
                 // Ejemplo: ADMIN y CLIENTE pueden gestionar sus propias reservas
                .requestMatchers("/api/reservas/**").hasAnyRole("ADMIN", "CLIENTE") // La lógica para "sus propias" se implementa en el servicio/controlador
                // Ejemplo: ADMIN puede gestionar facturas, pagos, detallefactura
                .requestMatchers("/api/facturas/**", "/api/pagos/**", "/api/detallefacturas/**").hasRole("ADMIN")

                // Cualquier otra solicitud requiere autenticación
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // Usar sesiones si es necesario (comportamiento por defecto)
            // .formLogin(Customizer.withDefaults()); // Habilita el login por formulario por defecto
            // .httpBasic(Customizer.withDefaults()); // Habilita autenticación básica HTTP por defecto
            .httpBasic(basic -> basic.realmName("Barbershop API")); // Configura autenticación básica con un nombre de reino

        return http.build();
    }

     // Se puede añadir un método para inicializar roles y un usuario admin al inicio si la base de datos está vacía
}

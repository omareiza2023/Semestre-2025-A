package com.corhuila.app_movil_g2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Barbershop API")
                        .version("1.0")
                        .description("API para agendar citas en una barbería")
                        .contact(new Contact()
                                .name("Your Name") // Tu nombre
                                .email("your.email@example.com") // Tu correo
                                .url("http://yourwebsite.com")) // Tu sitio web o el del proyecto
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                 // Define Security Scheme (Basic Auth)
                .components(new Components()
                    .addSecuritySchemes("basicAuth", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("basic")));
                // Puedes añadir .security(..) globalmente si todas las rutas requieren autenticación
    }
}

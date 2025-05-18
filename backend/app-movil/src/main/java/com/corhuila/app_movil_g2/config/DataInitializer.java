package com.corhuila.app_movil_g2.config;

import com.corhuila.app_movil_g2.Models.Persona;
import com.corhuila.app_movil_g2.Models.Rol;
import com.corhuila.app_movil_g2.Models.Usuario;
import com.corhuila.app_movil_g2.Repositories.IPersonaRepository;
import com.corhuila.app_movil_g2.Repositories.IRolRepository;
import com.corhuila.app_movil_g2.Repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IPersonaRepository personaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Crear Roles si no existen
        Rol adminRol = crearRolSiNoExiste("ADMIN");
        Rol barberoRol = crearRolSiNoExiste("BARBERO");
        Rol clienteRol = crearRolSiNoExiste("CLIENTE");

        // Crear Usuario Admin si no existe
        if (!usuarioRepository.existsByUsername("admin")) {
            Persona adminPersona = new Persona();
            adminPersona.setNombre("Admin");
            adminPersona.setApellido("User");
            adminPersona.setDocumento("00000000"); // Documento único o nulo si se permite
            adminPersona.setTelefono("0000000000"); // Teléfono único o nulo si se permite
            personaRepository.save(adminPersona);

            Usuario adminUsuario = new Usuario();
            adminUsuario.setUsername("admin");
            adminUsuario.setPassword(passwordEncoder.encode("admin123")); // Cambiar esta contraseña por defecto
            adminUsuario.setPersona(adminPersona);
            adminUsuario.setRol(adminRol);
            adminUsuario.setActivo(true);
            usuarioRepository.save(adminUsuario);
            System.out.println("Usuario administrador creado con username 'admin' y password 'admin123'");
        }
    }

    private Rol crearRolSiNoExiste(String nombreRol) {
        Optional<Rol> rolOpt = rolRepository.findByNombreRol(nombreRol);
        if (rolOpt.isEmpty()) {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombreRol(nombreRol);
            return rolRepository.save(nuevoRol);
        }
        return rolOpt.get();
    }
}

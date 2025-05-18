package com.corhuila.app_movil_g2.Services.impl;

import com.corhuila.app_movil_g2.Models.Persona;
import com.corhuila.app_movil_g2.Models.Rol;
import com.corhuila.app_movil_g2.Models.Usuario;
import com.corhuila.app_movil_g2.Repositories.IPersonaRepository;
import com.corhuila.app_movil_g2.Repositories.IRolRepository;
import com.corhuila.app_movil_g2.Repositories.IUsuarioRepository;
import com.corhuila.app_movil_g2.Services.IUsuarioService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository; // Para buscar el rol por defecto

    @Autowired
    private IPersonaRepository personaRepository; // Para manejar la Persona asociada

   @Autowired
   @Lazy // Esto rompe el ciclo
   private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllActive() {
        return usuarioRepository.findByActivoTrue();
    }

     @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

     @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByIdActive(Long id) {
        return usuarioRepository.findByIdAndActivoTrue(id);
    }


    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        // Asume que la contraseña ya viene hasheada si es una actualización,
        // o se hasheará en registerUser si es un nuevo registro.
        return usuarioRepository.save(usuario);
    }

     @Override
    @Transactional
    public Usuario registerUser(Usuario usuario) {
        // 1. Guardar o asociar la Persona
        if (usuario.getPersona() == null || usuario.getPersona().getId() == null) {
             // Si no viene una Persona existente, la guardamos
             if(usuario.getPersona() != null) {
                Persona nuevaPersona = personaRepository.save(usuario.getPersona());
                usuario.setPersona(nuevaPersona);
             } else {
                 // Manejar error o lanzar excepción si Persona es obligatoria
                 throw new IllegalArgumentException("Persona asociada es requerida para registrar un usuario.");
             }
        } else {
             // Verificar si la Persona existe
             Persona personaExistente = personaRepository.findById(usuario.getPersona().getId())
                 .orElseThrow(() -> new IllegalArgumentException("Persona con ID " + usuario.getPersona().getId() + " no encontrada."));
             usuario.setPersona(personaExistente);
        }


        // 2. Asignar Rol por defecto si no viene especificado (ej. Cliente)
        if (usuario.getRol() == null || usuario.getRol().getId() == null) {
             Rol rolCliente = rolRepository.findByNombreRol("CLIENTE")
                 .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado. Asegúrate de que los roles estén inicializados."));
             usuario.setRol(rolCliente);
        } else {
            // Verificar si el Rol existe
            Rol rolExistente = rolRepository.findById(usuario.getRol().getId())
                .orElseThrow(() -> new IllegalArgumentException("Rol con ID " + usuario.getRol().getId() + " no encontrado."));
             usuario.setRol(rolExistente);
        }


        // 3. Hashear la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // 4. Asegurar que esté activo por defecto
        usuario.setActivo(true);

        // 5. Guardar el usuario
        return usuarioRepository.save(usuario);
    }


    @Override
    @Transactional
    public void softDeleteById(Long id) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setActivo(false); // Marcar como inactivo
            usuarioRepository.save(usuario); // Guardar el cambio de estado
        }
        // Opcional: lanzar excepción si no se encuentra el usuario
        // else { throw new ResourceNotFoundException("Usuario no encontrado con id " + id); }
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
     @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }


    // --- Métodos de Spring Security UserDetailsService ---
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca el usuario activo por nombre de usuario (username)
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));
         // Spring Security verificará la contraseña y el estado isEnabled() que usa el campo 'activo'
    }

     // --- Métodos de Correo y Recuperación (Adaptados) ---

    // NOTA: findByEmail en el esquema original buscaba por email.
    // En el nuevo esquema, el email podría estar en la entidad Persona.
    // Usaremos findByUsername para la recuperación por ahora, asumiendo que el usuario ingresa su username (que podría ser un email si se define así).
    // Si necesitas buscar por un email específico en Persona, tendrías que añadir un campo email a Persona
    // y un método findByPersonaEmail en UsuarioRepository.
     @Override
     @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        // Asumimos que el "email" proporcionado es el "username" para buscar
        return findByUsername(email);
    }

    @Override
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true); // true = Permite contenido HTML
        helper.setFrom("your_gmail_email@gmail.com"); // Debe coincidir con spring.mail.username

        mailSender.send(message);
    }

    @Override
    public String generarContraseniaAleatoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder contraseña = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            int indice = random.nextInt(caracteres.length());
            contraseña.append(caracteres.charAt(indice));
        }
        return contraseña.toString();
    }

     // --- Métodos para buscar por Rol ---
     @Override
     @Transactional(readOnly = true)
     public List<Usuario> findBarberosActive() {
        return usuarioRepository.findByRolNombreRolAndActivoTrue("BARBERO");
     }

     @Override
     @Transactional(readOnly = true)
     public List<Usuario> findClientesActive() {
         return usuarioRepository.findByRolNombreRolAndActivoTrue("CLIENTE");
     }

     // Método para recuperación de contraseña (adaptado del original)
    @Transactional
    public String recuperarContraseña(String usernameOrEmail) throws MessagingException {
        Optional<Usuario> usuarioOptional = findByEmail(usernameOrEmail); // Usamos findByEmail que busca por username

        if (usuarioOptional.isEmpty()) {
             // Si no se encuentra, podemos intentar buscar por documento o teléfono si están en Persona
             // (Esto requiere adaptar findByEmail o crear un nuevo método)
             // Por ahora, solo lanzamos la excepción si no se encuentra por username/email
             throw new UsernameNotFoundException("Usuario no encontrado con username/email: " + usernameOrEmail);
        }

        Usuario usuario = usuarioOptional.get();

        String nuevaContrasenia = generarContraseniaAleatoria();
        String contraseniaHasheada = passwordEncoder.encode(nuevaContrasenia); // Usar el PasswordEncoder de Spring Security

        usuario.setPassword(contraseniaHasheada);
        save(usuario); // Guardar el usuario con la nueva contraseña

        String asunto = "Recuperación de contraseña";
        // Puedes obtener el email real desde la entidad Persona si existe un campo de email allí
        String emailDestino = usuario.getUsername(); // Usamos username como email por ahora

        sendEmail(emailDestino, asunto, "Tu nueva contraseña es: " + nuevaContrasenia);

        return "Se ha enviado un correo con la nueva contraseña a " + emailDestino;
    }
}

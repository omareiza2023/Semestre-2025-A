package com.map.parking_project.controllers;
import com.map.parking_project.models.User;
import com.map.parking_project.services.IUserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:8100"})
@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private IUserService userService;

    @GetMapping("/users")
    public List<User> index() {
        return userService.findAll();
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<User> show(@PathVariable Long id) {
        User user = userService.findById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userService.saveUser(user);
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Long id) {
        User currentUser = userService.findById(id);
        currentUser.setName(user.getName());
        currentUser.setLastname(user.getLastname());
        currentUser.setPhone(user.getPhone());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(user.getPassword());
        currentUser.setRol(user.getRol());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(currentUser));
    }
    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("/recuperarcontrasenia")
    public ResponseEntity<?> recuperarContraseña(@RequestParam String email) {
        User user = userService.findByEmail(email); // Busca un usuario por su correo electrónico

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Correo no registrado"); // Retorna error 404 si el usuario no existe
        }

        String nuevaContrasenia = userService.generarContraseniaAleatoria(); // Genera una nueva contraseña aleatoria

        user.setPassword(userService.ContraseniaSha256(nuevaContrasenia));
        // Asigna la nueva contraseña al usuario

        userService.save(user);
        // Guarda el usuario con la nueva contraseña

        String asunto = "Recuperación de contraseña";
        String cuerpo = "Tu nueva contraseña es: " + nuevaContrasenia;
        try {
            userService.sendEmail(email, asunto, cuerpo); // Envía la nueva contraseña por correo
            return ResponseEntity.ok("Se ha enviado un correo con la nueva contraseña.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo."); // Retorna error si falla el envío del correo
        }
    }

    @GetMapping("/send")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {
        try {
            userService.sendEmail(to, subject, body);
            // Envía un correo con los datos proporcionados
            return "Correo enviado correctamente a " + to;
        } catch (MessagingException e) {
            return "Error enviando el correo: " + e.getMessage();
            // Retorna un mensaje de error si el envío falla
        }
    }
}

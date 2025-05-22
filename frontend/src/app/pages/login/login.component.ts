import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { AuthService } from '../services/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [IonicModule, FormsModule],
})

  export class LoginComponent {
  username = '';
  password = '';
  errorMessage = '';
    
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {
    this.authService.login({ username: this.username, password: this.password })
      .subscribe({
        next: (response) => {
          this.authService.storeUserData(response);
          const role = this.authService.getRole();

          if (role === 'ROLE_ADMIN') {
            this.router.navigate(['/view-admin']);
          } else if (role === 'ROLE_BARBERO') {
            this.router.navigate(['/view-client']);
          } else if (role === 'ROLE_CLIENTE') {
            this.router.navigate(['/view-client']);
          } else {
            this.errorMessage = 'Rol no reconocido';
          }
        },
        error: () => {
          this.errorMessage = 'Credenciales incorrectas';
        }
      });
  

  iniciarSesion() {
    if (this.username && this.password) {
      // Aquí puedes poner la lógica de autenticación real
      console.log('Inicio de sesión exitoso');
    } else {
      alert("Campos obligatorios");
    }
}
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [IonicModule, FormsModule],
})
export class LoginComponent {
  email: string = '';
  password: string = '';

  constructor(private router: Router) {}

  iniciarSesion() {
    if (this.email && this.password) {
      // Aquí puedes poner la lógica de autenticación real
      console.log('Inicio de sesión exitoso');
    } else {
      alert("Campos obligatorios");
    }
  }

  registrar() {
    this.router.navigate(['/register']);
  }
}

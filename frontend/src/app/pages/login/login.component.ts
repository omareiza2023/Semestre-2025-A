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
  username = '';
  password = '';
  errorMessage = '';
  

  iniciarSesion() {
    if (this.username && this.password) {
      // Aquí puedes poner la lógica de autenticación real
      console.log('Inicio de sesión exitoso');
    } else {
      alert("Campos obligatorios");
    }
  }
}
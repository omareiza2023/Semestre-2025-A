import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms'; // Importa FormsModule

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: true,  // Indica que este componente es Standalone
  imports: [FormsModule] // Asegúrate de que FormsModule esté incluido
})
export class HomePage {
  rolSeleccionado: string = '';

  constructor(private router: Router) {}

  verRol() {
    if (this.rolSeleccionado) {
      this.router.navigate([`/${this.rolSeleccionado}`]);
    } else {
      alert('Por favor, seleccione un rol.');
    }
  }
}

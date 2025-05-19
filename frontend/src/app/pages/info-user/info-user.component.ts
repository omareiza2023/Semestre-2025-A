import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Usuario } from './user';

@Component({
  selector: 'app-info-user',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './info-user.component.html',
  styleUrls: ['./info-user.component.scss']
})
export class InfoUserComponent {
  usuario: Usuario = new Usuario();

  guardarCambios() {
    Swal.fire({
      title: '¿Estás seguro que deseas guardar los cambios?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, guardar',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: '¡Cambios guardados exitosamente!',
          icon: 'success',
          timer: 2000,
          showConfirmButton: false
        });
      }
    });
  }

  volver() {
    // Aquí va la lógica de navegación
  }
}

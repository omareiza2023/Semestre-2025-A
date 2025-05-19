import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-informacion-empresa',
  standalone: true,
  imports: [CommonModule, FormsModule, IonicModule],
  templateUrl: './informacion-empresa.component.html',
  styleUrls: ['./informacion-empresa.component.scss']
})
export class InformacionEmpresaComponent {
  horarios: string = '';
  ubicacion: string = '';
  ciudad: string = '';

  guardarInformacion() {
    if (!this.horarios || !this.ubicacion || !this.ciudad) {
      Swal.fire({
        title: 'Campos incompletos',
        text: 'Por favor complete toda la información',
        icon: 'warning',
        confirmButtonText: 'Ok'
      });
      return;
    }

    Swal.fire({
      title: '¿Seguro que desea guardar la información?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, guardar',
      cancelButtonText: 'No, seguir editando'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title: '¡Información guardada exitosamente!',
          icon: 'success',
          timer: 2000,
          showConfirmButton: false
        });

        // Aquí puedes agregar lógica para guardar la información en un servicio o backend
      } else {
        Swal.fire({
          title: 'Puede seguir editando.',
          icon: 'info',
          timer: 1500,
          showConfirmButton: false
        });
      }
    });
  }

  volver() {
    // Aquí irá la lógica de navegación con Ionic, por ejemplo:
    // this.router.navigate(['/ruta-anterior']);
  }
}

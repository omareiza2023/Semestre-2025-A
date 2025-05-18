import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-crear-servicio',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './crear-servicio.component.html',
  styleUrls: ['./crear-servicio.component.css']
})
export class CrearServicioComponent {
  nombreServicio: string = '';
  tarifa: number | null = null;

  crearServicio() {
    if (!this.nombreServicio || !this.tarifa) {
      Swal.fire({
        title: 'Campos incompletos',
        text: 'Por favor complete todos los campos',
        icon: 'warning',
        confirmButtonText: 'Ok'
      });
      return;
    }

    Swal.fire({
      title: '¿Desea crear este servicio?',
      text: `${this.nombreServicio} - $${this.tarifa} COP`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, crear',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        // Aquí se llamaría a un servicio real para guardar en base de datos
        Swal.fire({
          title: '¡Servicio creado exitosamente!',
          icon: 'success',
          timer: 2000,
          showConfirmButton: false
        });
        this.nombreServicio = '';
        this.tarifa = null;
      }
    });
  }

  volver() {
    // Navegación programática con router
    // Por ejemplo: this.router.navigate(['/servicios']);
    history.back(); // alternativa simple
  }
}

import { Component } from '@angular/core';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-actualizar-tarifa',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './actualizar-tarifa.component.html',
  styleUrls: ['./actualizar-tarifa.component.css']
})
export class ActualizarTarifaComponent {
  servicios = [
    { id: 1, nombre: 'Corte', tarifa: 25000 },
    { id: 2, nombre: 'Corte + Barba', tarifa: 45000 },
    { id: 3, nombre: 'Cejas', tarifa: 8000 },
    { id: 4, nombre: 'Manicura', tarifa: 40000 }
  ];
  
  servicioSeleccionado: number | null = null;
  nuevaTarifa: number | null = null;

  actualizarTarifa() {
    if (!this.servicioSeleccionado || !this.nuevaTarifa) {
      Swal.fire({
        title: 'Campos incompletos',
        text: 'Seleccione un servicio y defina una nueva tarifa',
        icon: 'warning',
        confirmButtonText: 'Ok'
      });
      return;
    }

    const servicio = this.servicios.find(s => s.id === this.servicioSeleccionado);

    Swal.fire({
      title: '¿Desea actualizar la tarifa?',
      text: `${servicio?.nombre} - Nueva tarifa: $${this.nuevaTarifa ?? 0} COP`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sí, actualizar',
      cancelButtonText: 'No, cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        servicio!.tarifa = this.nuevaTarifa ?? 0;

        Swal.fire({
          title: '¡Tarifa actualizada exitosamente!',
          icon: 'success',
          timer: 2000,
          showConfirmButton: false
        });

        this.servicioSeleccionado = null;
        this.nuevaTarifa = null;
      }
    });
  }

  volver() {
    // Aquí irá la lógica de navegación para volver al listado
  }
}

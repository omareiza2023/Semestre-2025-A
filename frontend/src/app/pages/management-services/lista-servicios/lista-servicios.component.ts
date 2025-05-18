import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-lista-servicios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-servicios.component.html',
  styleUrls: ['./lista-servicios.component.css']
})
export class ListaServiciosComponent {
  servicios = [
    { nombre: 'Corte', duracion: '30 min', precio: 25000, icono: '✂️' },
    { nombre: 'Corte + Barba', duracion: '60 min', precio: 45000, icono: '💇‍♂️' },
    { nombre: 'Cejas', duracion: '8 min', precio: 8000, icono: '👁️' },
    { nombre: 'Manicura', duracion: '60 min', precio: 40000, icono: '💅' },
  ];

  crearNuevoServicio() {
    // Reemplaza este console.log con la navegación real si tienes routing.
    console.log('Abrir formulario para nuevo servicio');
    // Ejemplo si usas routing:
    // this.router.navigate(['/crear-servicio']);
  }

  volver() {
    history.back(); // O reemplázalo con this.router.navigate(['/anterior']);
  }
}

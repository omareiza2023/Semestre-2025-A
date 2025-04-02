import { Component } from '@angular/core';

@Component({
  selector: 'app-recepcionista',
  templateUrl: './recepcionista.component.html',
  styleUrls: ['./recepcionista.component.scss'],
})
export class RecepcionistaComponent {
  recepcionista = {
    nombre: 'Carlos',
    apellido: 'García',
    edad: 28,
    correo: 'carlos.garcia@recepcion.com',
    horario: '8:00 AM - 5:00 PM',
    extension: '102',
  };

  agregar() {
    console.log('Agregar Recepcionista:', this.recepcionista);
  }

  modificar() {
    console.log('Modificar Recepcionista:', this.recepcionista);
  }

  eliminar() {
    console.log('Eliminar Recepcionista:', this.recepcionista);
  }

  consultar() {
    console.log('Consultar Recepcionista:', this.recepcionista);
  }
}

import { Component } from '@angular/core';

@Component({
  selector: 'app-enfermero',
  templateUrl: './enfermero.component.html',
  styleUrls: ['./enfermero.component.scss'],
})
export class EnfermeroComponent {
  enfermero = {
    nombre: 'Ana',
    apellido: 'López',
    edad: 34,
    correo: 'ana.lopez@hospital.com',
    turno: 'Mañana',
    area: 'Emergencias',
  };

  agregar() {
    console.log('Agregar Enfermero:', this.enfermero);
  }

  modificar() {
    console.log('Modificar Enfermero:', this.enfermero);
  }

  eliminar() {
    console.log('Eliminar Enfermero:', this.enfermero);
  }

  consultar() {
    console.log('Consultar Enfermero:', this.enfermero);
  }
}

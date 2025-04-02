import { Component } from '@angular/core';

@Component({
  selector: 'app-medico',
  templateUrl: './medico.component.html',
  styleUrls: ['./medico.component.scss'],
})
export class MedicoComponent {
  medico = {
    nombre: 'Juan',
    apellido: 'Pérez',
    edad: 45,
    correo: 'juan.perez@medico.com',
    especialidad: 'Cardiología',
    licenciaMedica: '12345678',
  };

  agregar() {
    console.log('Agregar Médico:', this.medico);
  }

  modificar() {
    console.log('Modificar Médico:', this.medico);
  }

  eliminar() {
    console.log('Eliminar Médico:', this.medico);
  }

  consultar() {
    console.log('Consultar Médico:', this.medico);
  }
}

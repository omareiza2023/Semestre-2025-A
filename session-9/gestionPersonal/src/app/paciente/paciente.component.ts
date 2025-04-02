import { Component } from '@angular/core';

@Component({
  selector: 'app-paciente',
  templateUrl: './paciente.component.html',
  styleUrls: ['./paciente.component.scss'],
})
export class PacienteComponent {
  paciente = {
    nombre: 'María',
    apellido: 'Ramírez',
    edad: 60,
    correo: 'maria.ramirez@paciente.com',
    historiaClinica: 'H123456789',
    afiliacion: 'EPS',
  };

  agregar() {
    console.log('Agregar Paciente:', this.paciente);
  }

  modificar() {
    console.log('Modificar Paciente:', this.paciente);
  }

  eliminar() {
    console.log('Eliminar Paciente:', this.paciente);
  }

  consultar() {
    console.log('Consultar Paciente:', this.paciente);
  }
}

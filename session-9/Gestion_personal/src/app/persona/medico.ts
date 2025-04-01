import { Component } from '@angular/core';
import { CardComponent } from "../card/card.component";

@Component({
  selector: 'app-medico',
  templateUrl: './medico.html',
  styleUrls: ['./medico.css'],
  standalone:true,
  imports: [CardComponent]
})
export class MedicoComponent {
  medico = {
    nombre: 'Dr. Juan Pérez',
    especialidad: 'Cardiología',
    telefono: '123-456-7890',
    correo: 'juan.perez@hospital.com'
  };
}
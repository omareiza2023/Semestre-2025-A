import { Component } from '@angular/core';

@Component({
  selector: 'app-persona',
  standalone: true,
  imports: [],
  templateUrl: './persona.component.html',
  styleUrl: './persona.component.css'
})
export class AppComponent {
  seleccionarRol(rol: string) {
    console.log(`Seleccionaste: ${rol}`);
  }
}

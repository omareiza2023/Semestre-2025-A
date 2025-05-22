import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { IONIC_STANDALONE_COMPONENTS } from '../../shared/ionic-standalone';

@Component({
  standalone: true,
  selector: 'app-agendar-cita',
  templateUrl: './agendar-cita.component.html',
  styleUrls: ['./agendar-cita.component.scss'],
  imports: [CommonModule, ReactiveFormsModule, ...IONIC_STANDALONE_COMPONENTS],
})
export class AgendarCitaComponent {
  form: FormGroup;
  fechasDisponibles = [new Date(), new Date(Date.now() + 86400000)]; // ejemplo
  horasDisponibles = ['9:00 - 11:00', '11:00 - 13:00', '14:00 - 16:00'];
  servicios = ['Corte de cabello', 'Afeitado', 'Peinado'];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      fecha: ['', Validators.required],
      hora: ['', Validators.required],
      servicio: ['', Validators.required],
      terminos: [false, Validators.requiredTrue],
    });
  }

  confirmarCita() {
    if (this.form.valid) {
      console.log('Datos de la cita:', this.form.value);
      // Aquí podrías hacer la llamada al backend
    }
  }
}

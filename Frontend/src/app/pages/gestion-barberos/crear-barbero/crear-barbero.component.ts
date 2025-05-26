import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-crear-barbero',
  templateUrl: './crear-barbero.component.html',
  styleUrls: ['./crear-barbero.component.scss'],
  standalone: true,
  imports: [CommonModule, IonicModule, ReactiveFormsModule]
})
export class CrearBarberoComponent {
  barberoForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private router: Router) {
    this.barberoForm = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      persona: this.fb.group({
        nombre: ['', Validators.required],
        apellido: ['', Validators.required],
        documento: ['', Validators.required],
        telefono: ['', Validators.required]
      })
    });
  }

  crearBarbero() {
    if (this.barberoForm.invalid) {
      this.barberoForm.markAllAsTouched();
      return;
    }

    this.http.post('https://pelican-alert-ram.ngrok-free.app/api/usuarios/registrar/barbero', this.barberoForm.value)
      .subscribe({
        next: () => this.router.navigate(['/barberos']),
        error: (err) => {
          console.error('Error al crear barbero', err);
          // Aquí puedes agregar manejo de errores UI si quieres
        }
      });
  }
}


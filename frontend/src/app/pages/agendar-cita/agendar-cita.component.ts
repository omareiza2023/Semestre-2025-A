import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ServicioService } from '../../services/servicio.service';
import { ReservaService } from '../../services/reserva.service';
import {
  IonDatetime,
  IonSelect,
  IonSelectOption,
  IonButton,
} from '@ionic/angular/standalone';

@Component({
  standalone: true,
  selector: 'app-agendar-cita',
  templateUrl: './agendar-cita.component.html',
  styleUrls: ['./agendar-cita.component.scss'],
  imports: [
    CommonModule,
    FormsModule,
    IonDatetime,
    IonSelect,
    IonSelectOption,
    IonButton
  ],
})
export class AgendarCitaComponent implements OnInit {
  fecha: string = '';
  hora: string = '';
  idServicio: number | null = null;
  idBarbero: number | null = null;

  horasDisponibles = ['09:00:00', '11:00:00', '14:00:00', '16:00:00'];
  servicios: any[] = [];
  barberos: any[] = [];

  username = 'admin';
  password = 'admin123';
  idUsuarioCliente = 2;

  constructor(
    private servicioService: ServicioService,
    private reservaService: ReservaService
  ) {}

  ngOnInit(): void {
    this.cargarServicios();
    this.cargarBarberos();
  }

  cargarServicios(): void {
    this.servicioService.obtenerServiciosActivos().subscribe({
      next: (data) => this.servicios = data,
      error: () => alert('Error al obtener servicios')
    });
  }

  cargarBarberos(): void {
    this.reservaService.obtenerBarberosActivos(this.username, this.password).subscribe({
      next: (data) => this.barberos = data,
      error: () => alert('Error al obtener barberos')
    });
  }

  confirmarCita(): void {
    if (!this.fecha || !this.hora || !this.idServicio || !this.idBarbero) {
      alert('Por favor complete todos los campos.');
      return;
    }

    const soloFecha = this.fecha.split('T')[0];
    const fechaHora = `${soloFecha}T${this.hora}`;

    const payload = {
      idUsuarioCliente: this.idUsuarioCliente,
      idServicio: this.idServicio,
      idUsuarioBarbero: this.idBarbero,
      fecha: fechaHora
    };

    this.reservaService.crearReserva(payload, this.username, this.password).subscribe({
      next: () => {
        alert('¡Cita agendada exitosamente!');
        this.fecha = '';
        this.hora = '';
        this.idServicio = null;
        this.idBarbero = null;
      },
      error: () => alert('No fue posible agendar la cita.')
    });
  }

  volver(): void {
    history.back();
  }
}





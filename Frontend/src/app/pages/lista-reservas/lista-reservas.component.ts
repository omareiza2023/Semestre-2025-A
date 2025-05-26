import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservaService } from '../../services/reserva.service';
import { IonList, IonItem, IonLabel, IonButton } from '@ionic/angular/standalone';

@Component({
  standalone: true,
  selector: 'app-lista-reservas',
  templateUrl: './lista-reservas.component.html',
  styleUrls: ['./lista-reservas.component.scss'],
  imports: [CommonModule, IonList, IonItem, IonLabel, IonButton],
})
export class ListaReservasComponent implements OnInit {
  reservas: any[] = [];
  username = 'admin';
  password = 'admin123';
  idUsuarioCliente = 2;

  constructor(private reservaService: ReservaService) {}

  ngOnInit(): void {
    this.cargarReservas();
  }

  cargarReservas(): void {
    this.reservaService.obtenerReservasActivasPorUsuario(
      this.idUsuarioCliente,
      this.username,
      this.password
    ).subscribe({
      next: (data) => (this.reservas = data),
      error: () => alert('Error al cargar las reservas')
    });
  }

  volver(): void {
    history.back();
  }
}
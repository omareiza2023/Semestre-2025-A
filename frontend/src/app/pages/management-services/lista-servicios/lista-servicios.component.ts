import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServicioService } from '../../../services/servicio.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lista-servicios',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-servicios.component.html',
  styleUrls: ['./lista-servicios.component.scss']
})
export class ListaServiciosComponent implements OnInit {
  servicios: any[] = [];

  constructor(private servicioService: ServicioService, private router: Router) {}

  ngOnInit(): void {
    this.cargarServicios();
  }

  cargarServicios(): void {
    this.servicioService.obtenerServiciosActivos().subscribe({
      next: (data) => {
        this.servicios = data;
      },
      error: (err) => {
        console.error('Error al obtener servicios:', err);
        alert('No se pudieron cargar los servicios.');
      }
    });
  }

  crearNuevoServicio() {
    this.router.navigate(['/crear-servicio']);
  }

  volver() {
    history.back();
  }
}

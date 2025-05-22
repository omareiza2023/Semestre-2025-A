import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ServicioService } from '../../../services/servicio.service';
import { IonSelect, IonSelectOption } from '@ionic/angular/standalone';

@Component({
  selector: 'app-actualizar-servicio',
  standalone: true,
  imports: [CommonModule, FormsModule, IonSelect,IonSelectOption],
  templateUrl: './actualizar-tarifas.component.html',
  styleUrls: ['./actualizar-tarifa.component.css']
})
export class ActualizarServicioComponent implements OnInit {
  servicios: any[] = [];
  categorias: any[] = [];

  servicioSeleccionado: any = null;
  nombre: string = '';
  descripcion: string = '';
  idCategoriaServicio: number | null = null;

  username: string = 'admin';
  password: string = 'admin123';

  constructor(private servicioService: ServicioService) {}

  ngOnInit(): void {
    this.cargarServicios();
    this.cargarCategorias();
  }

  cargarServicios(): void {
    this.servicioService.obtenerServiciosActivos().subscribe({
      next: (data) => this.servicios = data,
      error: () => alert('Error al cargar servicios')
    });
  }

  cargarCategorias(): void {
    this.servicioService.obtenerCategoriasActivas(this.username, this.password).subscribe({
      next: (data) => this.categorias = data,
      error: () => alert('Error al cargar categorías')
    });
  }

  seleccionarServicio(id: number): void {
    const servicio = this.servicios.find(s => s.id === id);
    if (servicio) {
      this.servicioSeleccionado = servicio;
      this.nombre = servicio.nombre;
      this.descripcion = servicio.descripcion;
      this.idCategoriaServicio = servicio.categoriaServicio?.id || null;
    }
  }

  actualizarServicio(): void {
    if (!this.servicioSeleccionado || !this.nombre || !this.descripcion || !this.idCategoriaServicio) {
      alert("Completa todos los campos");
      return;
    }

    const payload = {
      nombre: this.nombre,
      descripcion: this.descripcion,
      idCategoriaServicio: this.idCategoriaServicio
    };

    this.servicioService.actualizarServicioTarifa(
      this.servicioSeleccionado.id,
      payload,
      this.username,
      this.password
    ).subscribe({
      next: () => {
        alert('Servicio actualizado');
        this.servicioSeleccionado = null;
        this.nombre = '';
        this.descripcion = '';
        this.idCategoriaServicio = null;
        this.cargarServicios();
      },
      error: () => alert('Error al actualizar el servicio')
    });
  }

  volver(): void {
    history.back();
  }
}

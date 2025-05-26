import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ServicioService } from '../../../services/servicio.service';
import { IonItem, IonList, IonSelect, IonSelectOption } from '@ionic/angular/standalone';

@Component({
  selector: 'app-crear-servicio',
  standalone: true,
  imports: [CommonModule, FormsModule,IonItem, IonList, IonSelect, IonSelectOption],
  templateUrl: './crear-servicios.component.html',
  styleUrls: ['./crear-servicios.component.scss']
})
export class CrearServicioComponent implements OnInit {
  nombreServicio: string = '';
  descripcion: string = '';
  precio: string = '';
  idCategoriaServicio: number | null = null;
  username: string = 'admin';
  password: string = 'admin123';
  categorias: any[] = [];

  constructor(private servicioService: ServicioService) {}

  ngOnInit(): void {
    // solo se cargan si ya hay credenciales (o puedes cargar desde un servicio central)
    if (this.username && this.password) {
      this.cargarCategorias();
    }
  }

  cargarCategorias(): void {
    this.servicioService.obtenerCategoriasActivas(this.username, this.password).subscribe({
      next: (data) => {
        this.categorias = data;
      },
      error: (err) => {
        console.error('Error al cargar categorías:', err);
        alert('No se pudieron cargar las categorías activas.');
      }
    });
  }

  crearServicio(): void {
    if (!this.nombreServicio || !this.descripcion || !this.precio || !this.idCategoriaServicio) {
      alert('Por favor completa todos los campos.');
      return;
    }

    const confirmar = window.confirm(`¿Desea crear este servicio?\n\n${this.nombreServicio} - ${this.descripcion}`);

    if (!confirmar) return;

    const payload = {
      nombre: this.nombreServicio,
      descripcion: this.descripcion,
      precio: this.precio,
      idCategoriaServicio: this.idCategoriaServicio
    };

    this.servicioService.crearServicio(payload, this.username, this.password).subscribe({
      next: () => {
        alert('¡Servicio creado exitosamente!');
        this.nombreServicio = '';
        this.descripcion = '';
        this.idCategoriaServicio = null;
        this.categorias = [];
      },
      error: (err) => {
        console.error(err);
        alert('Error al crear el servicio.');
      }
    });
  }

  volver() {
    history.back();
  }
}

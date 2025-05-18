import { Component, EventEmitter, Output, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { IONIC_STANDALONE_COMPONENTS } from '../../shared/ionic-standalone';

/** Modelo de producto (simplificado) */
export interface Producto {
  id?: number;
  nombre: string;
  precio: number;
}

/** Estructura de respuesta del backend */
interface ApiResponse<T> {
  message: string;
  status: boolean;
  data: T;
}

@Component({
  standalone: true,
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  imports: [
    CommonModule,
    FormsModule,
    ...IONIC_STANDALONE_COMPONENTS,
  ],
})
export class ProductosComponente implements OnInit {

  /** Lista de productos cargados desde backend */
  lista: Producto[] = [];

  @Output() add = new EventEmitter<Producto>();

  private http = inject(HttpClient);

  ngOnInit() {
    this.http.get<ApiResponse<Producto[]>>('http://localhost:9000/api/v1/product')
      .subscribe({
        next: res => {
          if (res.status) this.lista = res.data;
        },
        error: err => {
          console.error('Error al cargar productos:', err);
        }
      });
  }
}

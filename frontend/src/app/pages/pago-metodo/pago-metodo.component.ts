import { Component, EventEmitter, Output, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { IONIC_STANDALONE_COMPONENTS } from '../../shared/ionic-standalone';

/** Modelo de respuesta del backend */
interface MetodoPagoApi {
  id: number;
  nomMetodo: string;
}

/** Estructura de respuesta genérica */
interface ApiResponse<T> {
  message: string;
  status: boolean;
  data: T;
}

@Component({
  standalone: true,
  selector: 'app-pago-metodo',
  templateUrl: './pago-metodo.component.html',
  imports: [
    CommonModule,
    FormsModule,
    ...IONIC_STANDALONE_COMPONENTS,
  ],
})
export class PagoMetodoComponent implements OnInit {

  metodos: string[] = [];
  elegido: string = '';

  @Output() select = new EventEmitter<string>();
  private http = inject(HttpClient);

  ngOnInit() {
    this.http.get<ApiResponse<MetodoPagoApi[]>>('http://localhost:9000/api/v1/metodopago')
      .subscribe({
        next: (res) => {
          if (res.status && res.data.length) {
            this.metodos = res.data.map(m => m.nomMetodo);
            this.elegido = this.metodos[0]; // selecciona el primero por defecto
            this.select.emit(this.elegido);
          }
        },
        error: (err) => {
          console.error('❌ Error al cargar métodos de pago:', err);
        }
      });
  }
}

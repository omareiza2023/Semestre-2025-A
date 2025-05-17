import { Component, EventEmitter, Input, Output, inject } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';    // para ngModel
import { Producto } from '../productos/productos.component';
import { IONIC_STANDALONE_COMPONENTS } from '../../shared/ionic-standalone';

export interface EncabezadoFactura {
  cliente: string;
  nit: string;
  fecha: string;
}
@Component({
  standalone: true,                       // 👈 1) es un componente autónomo
  selector: 'app-factura-encabezado',
  templateUrl: './factura-encabezado.component.html',
  imports: [                              // 👈 2) módulos que usa su plantilla
    CommonModule,
    FormsModule,
    HttpClientModule,
    ...IONIC_STANDALONE_COMPONENTS,
  ],
  /* 👇  aquí registras el provider */
  providers: [DatePipe],
})
export class FacturaEncabezadoComponent {
  /* ----------  NUEVO  ---------- */
  /** Modelo que la vista edita */
  @Input() data: EncabezadoFactura = { cliente: '', nit: '', fecha: '' };
  /** Se emite cada vez que el modelo cambia */
  @Output() change = new EventEmitter<EncabezadoFactura>();
  carrito: Producto[] = [];
  encabezado?: EncabezadoFactura;
  pago?: string;


  /* ✅ Inyección para usarlo en el TS */
  private datePipe = inject(DatePipe);
  private http = inject(HttpClient);

  get total(): number {
    return this.carrito.reduce((s, p) => s + p.precio, 0);
  }

  confirmar() {
    if (!this.data) return;

    const factura = {
      ...this.data,
      status: true,
    };
    this.data.fecha = this.datePipe.transform(new Date(), 'yyyy-MM-dd')!;
    console.log('👈 Enviando factura:', factura);

    this.http.post('http://localhost:9000/api/v1/factura', factura)
      .subscribe({
        next: res => {
          alert('✅ Factura enviada:\n\n' + JSON.stringify(res, null, 2));
        },
        error: err => {
          console.error('❌ Error al enviar factura:', err);
          alert('Error al enviar la factura.');
        }
      });
  }
}

import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IONIC_STANDALONE_COMPONENTS } from '../../shared/ionic-standalone';

import { ProductosComponente, Producto } from '../productos/productos.component';
import { FacturaEncabezadoComponent, EncabezadoFactura } from '../factura-encabezado/factura-encabezado.component';
import { PagoMetodoComponent } from '../pago-metodo/pago-metodo.component';

@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  imports: [
    CommonModule,
    ProductosComponente,
    FacturaEncabezadoComponent,
    PagoMetodoComponent,
    ...IONIC_STANDALONE_COMPONENTS,
  ],
})
export class HomeComponent {
  @ViewChild(FacturaEncabezadoComponent)
  private encabezadoCmp!: FacturaEncabezadoComponent;
  carrito: Producto[] = [];
  encabezado?: EncabezadoFactura;
  pago?: string;
  get total(): number {
    return this.carrito.reduce((s, p) => s + p.precio, 0);
  }
  ngAfterViewInit() {
  }
  confirmarCompra() {
    this.encabezadoCmp.confirmar();
  }
}

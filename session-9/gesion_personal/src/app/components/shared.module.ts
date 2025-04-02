import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BotonesComponent } from '../botones/botones.component';// Asegúrate de que la ruta sea correcta

@NgModule({
  declarations: [BotonesComponent],
  exports: [BotonesComponent],  // Exportar el componente
  imports: [CommonModule]
})
export class SharedModule { }
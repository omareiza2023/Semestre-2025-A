import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-botones',
  templateUrl: './botones.component.html',
  styleUrls: ['./botones.component.scss']
})
export class BotonesComponent {
  @Output() agregar = new EventEmitter<void>();
  @Output() modificar = new EventEmitter<void>();
  @Output() eliminar = new EventEmitter<void>();
  @Output() consultar = new EventEmitter<void>();
}

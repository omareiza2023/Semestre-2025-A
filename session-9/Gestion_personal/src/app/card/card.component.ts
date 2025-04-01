import { Component, EventEmitter, Output } from '@angular/core';
import { BotonesComponent } from "../botones/botones.component";

@Component({
  selector: 'app-card',
  standalone: true,
  imports: [BotonesComponent],
  templateUrl: './card.component.html',
  styleUrl: './card.component.css'
})
export class CardComponent {
  @Output() save = new EventEmitter<void>();
  @Output() cancel = new EventEmitter<void>();
  @Output() edit = new EventEmitter<void>();
  @Output() delete = new EventEmitter<void>();
}

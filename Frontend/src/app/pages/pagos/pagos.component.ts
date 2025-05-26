import { Component, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-payment',
  imports: [CommonModule],
  templateUrl: './pagos.component.html',
  styleUrls: ['./pagos.component.css']
})
export class PagosComponent implements AfterViewInit {

  popupVisible: boolean = false;
  estadoTexto: string = 'Loading...';

  mostrarCarga(): void {
    this.popupVisible = true;
    this.estadoTexto = 'Loading...';

    setTimeout(() => {
      this.estadoTexto = 'Pago exitoso';
    }, 8000);
  }

  ngAfterViewInit(): void {
    this.setupCvcToggle();
    this.setupCustomPopover();
  }

  private setupCvcToggle(): void {
    const cvcIcon = document.getElementById('cvc');
    const cvcContainer = document.querySelector('.cvc-preview-container');

    if (cvcIcon && cvcContainer) {
      cvcIcon.addEventListener('click', () => {
        cvcContainer.classList.toggle('hide');
      });

      cvcContainer.addEventListener('click', () => {
        cvcContainer.classList.add('hide');
      });
    }
  }

  private setupCustomPopover(): void {
    const triggers = document.querySelectorAll<HTMLElement>('[data-toggle="popover"]');

    triggers.forEach(trigger => {
      const content = trigger.getAttribute('data-content') || '';

      const popover = document.createElement('div');
      popover.className = 'custom-popover';
      popover.innerText = content;
      document.body.appendChild(popover);

      trigger.addEventListener('focus', () => {
        const rect = trigger.getBoundingClientRect();
        popover.style.top = `${rect.top + window.scrollY}px`;
        popover.style.left = `${rect.left - popover.offsetWidth - 10 + window.scrollX}px`;
        popover.classList.add('visible');
      });

      trigger.addEventListener('blur', () => {
        popover.classList.remove('visible');
      });
    });
  }
}
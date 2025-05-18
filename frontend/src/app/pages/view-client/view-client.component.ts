import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-vista-cliente',
  standalone: true,
  imports: [CommonModule, IonicModule, RouterModule],
  templateUrl: './view-client.component.html',
  styleUrls: ['./view-client.component.scss']
})
export class ViewClientComponent {

  navigateToWhatsApp() {
    window.open('https://wa.me/3001234567', '_blank');
  }

  navigateToGoogleMaps() {
    window.open('https://www.google.com/maps?q=Barberia+Golden+Huila', '_blank');
  }

  confirmDeleteAccount() {
    Swal.fire({
      title: '¿Está seguro que desea borrar la cuenta?',
      text: "Esta acción no se puede deshacer",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          '¡Cuenta eliminada!',
          'Su cuenta ha sido eliminada exitosamente.',
          'success'
        );
        // Aquí debes llamar el servicio de eliminación y redirigir al login
      }
    });
  }

  confirmLogout() {
    Swal.fire({
      title: '¿Seguro que quieres cerrar sesión?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, cerrar sesión',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire(
          'Sesión cerrada',
          'Se ha cerrado la sesión correctamente.',
          'success'
        );
        window.location.href = '/login';
      }
    });
  }
}

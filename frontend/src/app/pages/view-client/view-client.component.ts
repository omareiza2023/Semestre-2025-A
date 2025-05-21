import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';

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
    alert("cuenta eliminada")
  }

  confirmLogout() {
    window.location.href = '/login';
  }
}

import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-vista-admin',
  standalone: true,
  imports: [CommonModule, IonicModule, RouterModule],
  templateUrl: './view-admin.component.html',
  styleUrls: ['./view-admin.component.scss'],
})
export class ViewAdminComponent {
  constructor(private router: Router) {}

  navigateTo(route: string) {
    this.router.navigate([route]);
  }

  cerrarSesion() {
    this.router.navigate(['/login']);
  }
}


import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { RouterModule, Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-barberos',
  templateUrl: './barberos.component.html',
  styleUrls: ['./barberos.component.scss'],
  standalone: true,
  imports: [CommonModule, IonicModule, RouterModule]
})
export class BarberosComponent implements OnInit {
  username = 'admin';
  password = 'admin123';
  idUsuarioCliente = 2;
  barberos: any[] = [];

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.obtenerBarberos();
  }

  obtenerBarberos() {
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa(`${this.username}:${this.password}`),
      'ngrok-skip-browser-warning': 'true'
    });
    this.http.get<any[]>('https://pelican-alert-ram.ngrok-free.app/api/usuarios/barberos/activos', { headers }) // Ajusta esta URL según tu backend
      .subscribe(data => {
        this.barberos = data;
      });
  }

/*   editarBarbero(id: number) {
    this.router.navigate(['/editar-barbero', id]);
  }

  eliminarBarbero(id: number): void {
    const url = https://pelican-alert-ram.ngrok-free.app/api/usuarios/${id}/inactivar;
    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + btoa(${this.username}:${this.password}),
      'ngrok-skip-browser-warning': 'true'
    });
    this.http.patch(url, {}, { headers })
      .subscribe({
        next: () => {
          // refresca la lista o notifica éxito
          this.obtenerBarberos();
        },
        error: err => {
          console.error('Error al inactivar barbero', err);
          alert('No se pudo inactivar el barbero. Intenta de nuevo.');
        }
      });
  }
 */
  crearBarbero() {
    this.router.navigate(['/crear-barbero']);
  }
}
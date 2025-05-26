import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface Categoria {
  id: number;
  nombre: string;
  descripcion: string;
  activo: boolean;
}

@Component({
  selector: 'app-categorias',
  templateUrl: './categorias.component.html',
  styleUrls: ['./categorias.component.scss'],
  standalone: true,
  imports: [CommonModule]
})
export class CategoriasComponent implements OnInit {

  categorias: Categoria[] = [];
  baseUrl = 'https://pelican-alert-ram.ngrok-free.app/api';
  private credentials = {
    username: 'admin',
    password: 'admin123'
  };

  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarCategorias();
  }

  private getHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Authorization': 'Basic ' + btoa(`${this.credentials.username}:${this.credentials.password}`),
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': 'true',
      'Accept': 'application/json'
    });
  }

  cargarCategorias(): void {
    const headers = this.getHeaders();

    this.http.get<Categoria[]>(`${this.baseUrl}/categoriaservicios`, { headers }).subscribe({
      next: (data) => {
        console.log('Categorías cargadas:', data);
        this.categorias = data.filter(cat => cat.activo);
      },
      error: (error) => {
        console.error('Error al cargar categorías:', error);
        if (error.status === 0) {
          alert('Error de conexión con el servidor. Por favor, verifica tu conexión a internet.');
        }
      }
    });
  }

  crearCategoria(): void {
    this.router.navigate(['/crear-categoria']);
  }

  editarCategoria(id: number): void {
    this.router.navigate(['/editar-categoria', id]);
  }

  eliminarCategoria(id: number): void {
    if (confirm('¿Estás seguro de que deseas eliminar esta categoría?')) {
      const headers = this.getHeaders();
      const options = { headers };

      this.http.patch(`${this.baseUrl}/categoriaservicios/${id}/inactivar`, {}, options).subscribe({
        next: () => {
          console.log('Categoría inactivada correctamente');
          alert('Categoría eliminada exitosamente');
          this.cargarCategorias();
        },
        error: (error) => {
          console.error('Error al intentar inactivar la categoría:', error);
          
          if (error.status === 0) {
            alert('Error de conexión con el servidor. Por favor, verifica tu conexión a internet.');
          } else if (error.status === 500) {
            alert('Error en el servidor. Por favor, inténtalo de nuevo más tarde.');
          } else {
            alert('No se pudo inactivar la categoría. Por favor, inténtalo de nuevo.');
          }
        }
      });
    }
  }
}
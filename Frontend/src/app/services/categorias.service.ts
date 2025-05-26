import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriasService {
  private apiUrl = 'https://pelican-alert-ram.ngrok-free.app';

  constructor(private http: HttpClient) { }

  // Crear una nueva categoría
  createCategoria(categoria: any, username: string, password: string): Observable<any> {
    const headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa(`${username}:${password}`),
        'ngrok-skip-browser-warning': 'true'
    });
    return this.http.post(`${this.apiUrl}/api/categoriaservicios`, categoria, { headers });
  }
}
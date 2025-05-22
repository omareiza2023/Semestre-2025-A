import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const API_URL = 'http://localhost:8080/api/usuarios';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  registrarCliente(data: any): Observable<any> {
    return this.http.post(`${API_URL}/registrar/cliente`, data);
  }
}

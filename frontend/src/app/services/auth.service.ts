
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environment';

export interface LoginResponse {
  username: string;
  message: string;
  roles: string[];
}

const API_URL = 'http://localhost:8080/api/usuarios';
@Injectable({
  providedIn: 'root'
})

export class AuthService {

  private baseUrl = environment.loginUrl;

  constructor(private http: HttpClient) {}

  login(credentials: { username: string; password: string }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.baseUrl}/login`, credentials);
  }

  storeUserData(response: LoginResponse): void {
    localStorage.setItem('username', response.username);
    localStorage.setItem('roles', JSON.stringify(response.roles));
  }

  getRoles(): string[] {
    const roles = localStorage.getItem('roles');
    return roles ? JSON.parse(roles) : [];
  }

  getRole(): string {
    return this.getRoles()[0]; // Si solo tienes un rol por usuario
  }

  isAdmin(): boolean {
    return this.getRole() === 'ROLE_ADMIN';
  }

  isBarbero(): boolean {
    return this.getRole() === 'ROLE_BARBERO';
  }

  isCliente(): boolean {
    return this.getRole() === 'ROLE_CLIENTE';
  }

  logout(): void {
    localStorage.clear();
  }


   registrarCliente(data: any): Observable<any> {
    return this.http.post(`${API_URL}/registrar/cliente`, data);
  }
}
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ReservaService {
    private baseUrl = 'http://localhost:8080/api/reservas';

    constructor(private http: HttpClient) { }

    crearReserva(data: any, username: string, password: string): Observable<any> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa(`${username}:${password}`)
        });
        return this.http.post(this.baseUrl, data, { headers });
    }

    obtenerBarberosActivos(username: string, password: string): Observable<any[]> {
        const headers = new HttpHeaders({
            'Authorization': 'Basic ' + btoa(`${username}:${password}`)
        });
        return this.http.get<any[]>('http://localhost:8080/api/usuarios/barberos/activos', { headers });
    }


    obtenerReservasActivasPorUsuario(idUsuario: number, username: string, password: string): Observable<any[]> {
        const headers = new HttpHeaders({
            Authorization: 'Basic ' + btoa(`${username}:${password}`)
        });
        return this.http.get<any[]>(`http://localhost:8080/api/reservas/usuario/${idUsuario}/activas`, { headers });
    }
}


import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ReservaService {
    private baseUrl = 'https://pelican-alert-ram.ngrok-free.app/api/reservas';

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
        'Authorization': 'Basic ' + btoa(`${username}:${password}`),
        'ngrok-skip-browser-warning': 'true'
    });
    return this.http.get<any[]>('https://pelican-alert-ram.ngrok-free.app/api/usuarios/barberos/activos', { headers });
    }

    obtenerReservasActivasPorUsuario(idUsuario: number, username: string, password: string): Observable<any[]> {
    const headers = new HttpHeaders({
        'Authorization': 'Basic ' + btoa(`${username}:${password}`),
        'ngrok-skip-browser-warning': 'true'
    });
    return this.http.get<any[]>(`https://pelican-alert-ram.ngrok-free.app/api/reservas/usuario/${idUsuario}/activas`, { headers });
    }
}

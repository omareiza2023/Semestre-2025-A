import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class ServicioService {
    private baseUrl = 'https://pelican-alert-ram.ngrok-free.app/api/';

    constructor(private http: HttpClient) { }

    crearServicio(data: any, username: string, password: string): Observable<any> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa(`${username}:${password}`)
        });

        return this.http.post(`${this.baseUrl}servicios`, data, { headers });
    }

    obtenerCategoriasActivas(username: string, password: string): Observable<any[]> {
        const headers = new HttpHeaders({
            Authorization: 'Basic ' + btoa(`${username}:${password}`),
            'ngrok-skip-browser-warning': 'true'
        });
        return this.http.get<any[]>(`${this.baseUrl}categoriaservicios`, { headers });
    }


    obtenerServiciosActivos(): Observable<any[]> {
        const headers = new HttpHeaders({
            'ngrok-skip-browser-warning': 'true'
        });

        return this.http.get<any[]>(`${this.baseUrl}servicios/activos`, { headers });
    }



    actualizarServicioTarifa(id: number, data: any, username: string, password: string): Observable<any> {
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa(`${username}:${password}`),
            'ngrok-skip-browser-warning': 'true'
        });
        return this.http.put(`${this.baseUrl}servicios/${id}`, data, { headers });
    }

}
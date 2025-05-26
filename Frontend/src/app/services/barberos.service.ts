import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class BarberosService {
    // Ajusta aquí tu baseUrl (puede salir de environment.ts)
    private baseUrl = 'https://pelican-alert-ram.ngrok-free.app/api/';

    constructor(private http: HttpClient) { }

    registrarBarbero(barberoData: any,username: string, password: string): Observable<any> {
        const url = `${this.baseUrl}usuarios/registrar/barbero`;
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': 'Basic ' + btoa(${username}:${password}),
            // ngrok-free pide a veces este header para evitar la advertencia en el navegador
            'ngrok-skip-browser-warning': 'true'
        });
        return this.http.post(url, barberoData, { headers });
    }
}
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../pages/info-user/user';

@Injectable({
    providedIn: 'root'
})
export class UsuarioService {
    private baseUrl = 'https://pelican-alert-ram.ngrok-free.app/api/';

    constructor(private http: HttpClient) { }

    obtenerUsuarioPorId(
        id: number,
        username: string,
        password: string
    ): Observable<Usuario> {
        const url = `${this.baseUrl}usuarios/2`;
        const headers = new HttpHeaders({
            'Authorization': 'Basic ' + btoa(`${username}:${password}`),
            'ngrok-skip-browser-warning': 'true'
        });
        return this.http.get<Usuario>(url, { headers });
    }

    // aquí podrías agregar más métodos (actualizarUsuario, etc.)
}
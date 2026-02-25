import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UsuarioResumen {
    idUsuario: number;
    tipoDoc: string;
    nroDoc: string;
    nombres: string;
    apellidos: string;
    telefono?: string;
    email?: string;
    activo?: boolean;
    roles: string[]; // ["ROLE_CLIENTE", "ROLE_ENTRENADOR"]
}

@Injectable({
    providedIn: 'root'
})
export class Usuarios {

    private baseUrl = 'http://localhost:8080/api/usuarios';

    constructor(private http: HttpClient) {}

    getUsuariosResumen(): Observable<UsuarioResumen[]> {
        return this.http.get<UsuarioResumen[]>(`${this.baseUrl}/resumen`);
    }

    crearUsuario(usuario: any): Observable<any> {
        return this.http.post<any>('http://localhost:8080/api/usuarios/crear', usuario);
    }
}

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UsuarioResumen {
    id: string;
    tipoDoc: string;
    nroDoc: string;
    numDoc: string;
    nombres: string;
    apellidos: string;
    telefono?: string;
    correo?: string;
    activo?: boolean;
    roles: string[]; // ["ROLE_CLIENTE", "ROLE_ENTRENADOR"]
}

@Injectable({
    providedIn: 'root'
})
export class Usuarios {

    private baseUrl = 'https://ironfit-backend-production.up.railway.app/api/usuarios';

    constructor(private http: HttpClient) {}

    getUsuariosResumen(): Observable<UsuarioResumen[]> {
        return this.http.get<UsuarioResumen[]>(`${this.baseUrl}/resumen`);
    }

    crearUsuario(usuario: any): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/crear`, usuario);
    }
    cambiarEstado(id: string, activo: boolean): Observable<UsuarioResumen> {
    return this.http.patch<UsuarioResumen>(`${this.baseUrl}/${id}/estado`, { activo });
    }

    getMiPerfil(): Observable<UsuarioResumen> {
        return this.http.get<UsuarioResumen>(`${this.baseUrl}/me`);
    }

    actualizarMiPerfil(data: any) {
    return this.http.patch<UsuarioResumen>(`${this.baseUrl}/me`, data);
    }

    cambiarMiPassword(nuevaPassword: string) {
    return this.http.patch(`${this.baseUrl}/me/password`, nuevaPassword, {
        headers: { 'Content-Type': 'text/plain' }
    });
    }

    resetearPassword(id: string) {
    return this.http.patch<UsuarioResumen>(`${this.baseUrl}/${id}/resetear-password`, {});
    }
}

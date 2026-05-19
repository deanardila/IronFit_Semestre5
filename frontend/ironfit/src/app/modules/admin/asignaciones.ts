import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

    export interface AsignacionEntrenadorClienteDTO {
    id: string;

    clienteId: string;
    clienteNombre: string;
    clienteCorreo: string;
    clienteDocumento: string;

    entrenadorId: string;
    entrenadorNombre: string;
    entrenadorCorreo: string;

    activo: boolean;
    fechaAsignacion: string;
    fechaActualizacion: string;
    }

    export interface AsignacionEntrenadorClienteRequest {
    clienteId: string;
    entrenadorId: string;
    }

    @Injectable({
    providedIn: 'root',
    })
    export class AsignacionesService {
    private apiUrl = 'https://ironfit-backend-production.up.railway.app/api/asignaciones';

    constructor(private http: HttpClient) {}

    listarAsignaciones(): Observable<AsignacionEntrenadorClienteDTO[]> {
        return this.http.get<AsignacionEntrenadorClienteDTO[]>(this.apiUrl);
    }

    listarMisClientes(): Observable<AsignacionEntrenadorClienteDTO[]> {
        return this.http.get<AsignacionEntrenadorClienteDTO[]>(`${this.apiUrl}/mis-clientes`);
    }

    crearAsignacion(request: AsignacionEntrenadorClienteRequest): Observable<AsignacionEntrenadorClienteDTO> {
        return this.http.post<AsignacionEntrenadorClienteDTO>(this.apiUrl, request);
    }

    cambiarEstado(id: string, activo: boolean): Observable<AsignacionEntrenadorClienteDTO> {
        return this.http.patch<AsignacionEntrenadorClienteDTO>(
        `${this.apiUrl}/${id}/estado?activo=${activo}`,
        {}
        );
    }
}
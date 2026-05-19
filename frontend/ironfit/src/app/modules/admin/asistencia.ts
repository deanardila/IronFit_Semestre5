import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AsistenciaReporteDTO {
    id: string;

    clienteId: string;
    clienteNombre: string;
    clienteDocumento: string;

    entrenadorId: string;
    entrenadorNombre: string;

    planId: string;
    planNombre: string;

    rutinaId: string;
    rutinaNombre: string;

    fechaAsistencia: string;

    asistio: boolean;
    cumplioRutina: boolean;

    estadoAsistencia: string;
    observaciones: string;
    }

    export interface FiltrosAsistencia {
    fechaInicio?: string;
    fechaFin?: string;
    clienteId?: string;
    entrenadorId?: string;
    estado?: string;
    }

    @Injectable({
    providedIn: 'root',
    })
    export class AsistenciaService {
    private apiUrl = 'https://ironfit-backend-production.up.railway.app/api/asistencias';

    constructor(private http: HttpClient) {}

    obtenerReporteAsistencias(filtros?: FiltrosAsistencia): Observable<AsistenciaReporteDTO[]> {
        let params = new HttpParams();

        if (filtros?.fechaInicio) {
        params = params.set('fechaInicio', filtros.fechaInicio);
        }

        if (filtros?.fechaFin) {
        params = params.set('fechaFin', filtros.fechaFin);
        }

        if (filtros?.clienteId) {
        params = params.set('clienteId', filtros.clienteId);
        }

        if (filtros?.entrenadorId) {
        params = params.set('entrenadorId', filtros.entrenadorId);
        }

        if (filtros?.estado && filtros.estado !== 'TODOS') {
        params = params.set('estado', filtros.estado);
        }

        return this.http.get<AsistenciaReporteDTO[]>(`${this.apiUrl}/reportes`, { params });
  }
}
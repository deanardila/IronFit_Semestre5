import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PlanEntrenamientoDTO {
    clienteNombre?: string;
    entrenadorNombre?: string;
    id: string;
    nombre: string;
    descripcion: string;
    objetivo: string;
    fechaInicio: string;
    fechaFin: string;
    clienteId: string;
    entrenadorId: string;
    activo: boolean;
    fechaCreacion: string;
    fechaActualizacion: string;
}

export interface PlanEntrenamientoCrearDTO {
    nombre: string;
    descripcion: string;
    objetivo: string;
    fechaInicio: string;
    fechaFin: string;
    clienteId: string;
    entrenadorId: string;
}

export interface PlanResumen {
    idPlan: number;
    nombrePlan: string;
    nombreCliente: string;
    nombreEntrenador: string;
    estado: string | { name?: string };
    fechaInicio?: string;
    fechaFin?: string;
}

export interface RutinaResumen {
    idRutina: number;
    nombre: string;
    descripcion?: string;
}

@Injectable({
    providedIn: 'root',
})
export class Planes {
    private apiUrl = 'http://localhost:8081/api/planes';

    constructor(private http: HttpClient) {}

    getPlanes(): Observable<PlanEntrenamientoDTO[]> {
        return this.http.get<PlanEntrenamientoDTO[]>(this.apiUrl);
    }

    getResumenPlanes(): Observable<PlanResumen[]> {
        return this.http.get<PlanResumen[]>(`${this.apiUrl}/resumen`);
    }

    getRutinasPorPlan(idPlan: number): Observable<RutinaResumen[]> {
        return this.http.get<RutinaResumen[]>(`${this.apiUrl}/${idPlan}/rutinas`);
    }

    crearPlan(plan: PlanEntrenamientoCrearDTO): Observable<PlanEntrenamientoDTO> {
        return this.http.post<PlanEntrenamientoDTO>(this.apiUrl, plan);
    }

    actualizarPlan(id: string, plan: PlanEntrenamientoCrearDTO): Observable<PlanEntrenamientoDTO> {
        return this.http.put<PlanEntrenamientoDTO>(`${this.apiUrl}/${id}`, plan);
    }

    cambiarEstado(id: string, activo: boolean): Observable<void> {
        return this.http.patch<void>(`${this.apiUrl}/${id}/estado?activo=${activo}`, {});
    }
}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PlanResumen {
    idPlan: number;
    nombrePlan: string;
    estado: string;
    fechaInicio: string;
    fechaFin: string;
    idCliente: number;
    nombreCliente: string;
    idEntrenador: number;
    nombreEntrenador: string;
}

@Injectable({ providedIn: 'root' })
export class Planes {
    private baseUrl = 'http://localhost:8080/api/planes';

    constructor(private http: HttpClient) {}

    getResumenPlanes(): Observable<any[]> {
        return this.http.get<any[]>(`${this.baseUrl}/resumen`);
    }

    getPlanesPorCliente(idCliente: number): Observable<any[]> {
        return this.http.get<any[]>(`${this.baseUrl}/cliente/${idCliente}`);
    }

    getPlanesPorEntrenador(idEntrenador: number): Observable<any[]> {
        return this.http.get<any[]>(`${this.baseUrl}/entrenador/${idEntrenador}`);
    }

    // Obtener rutinas asociadas a un plan
    getRutinasPorPlan(idPlan: number): Observable<any[]> {
        // El endpoint de rutinas está en /api/planes/{id}/rutinas
        return this.http.get<any[]>(`${this.baseUrl}/${idPlan}/rutinas`);
    }

    
}

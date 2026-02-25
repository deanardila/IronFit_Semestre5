import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class Dashboard {

    private baseUrl = 'http://localhost:8080/api/dashboard';

    constructor(private http: HttpClient) {}

    // ADMIN
    getClientesActivos(): Observable<number> {
        return this.http.get<number>(`${this.baseUrl}/clientes-activos/total`);
    }

    getEntrenadoresActivos(): Observable<number> {
        return this.http.get<number>(`${this.baseUrl}/entrenadores-activos/total`);
    }

    getEjerciciosRegistrados(): Observable<number> {
        return this.http.get<number>(`${this.baseUrl}/ejercicios/total`);
    }

    getAuditoriasMes(): Observable<number> {
        return this.http.get<number>(`${this.baseUrl}/auditorias/mes`);
    }

    // CLIENTE
    getPlanActual(): Observable<string> {
        return this.http.get<string>(`${this.baseUrl}/cliente/plan-actual`);
    }

    getAsistenciasEsteMes(): Observable<number> {
        return this.http.get<number>(`${this.baseUrl}/cliente/asistencias/mes`);
    }

    getRutinasActivas(): Observable<number> {
        return this.http.get<number>(`${this.baseUrl}/cliente/rutinas-activas`);
    }

    getProgresoGeneral(): Observable<string> {
        return this.http.get<string>(`${this.baseUrl}/cliente/progreso`);
    }

    // ENTRENADOR
    getSesionesEsteMes(): Observable<number> {
        return this.http.get<number>(`${this.baseUrl}/entrenador/sesiones/mes`);
    }

    getPlanesActivos(): Observable<number> {
        return this.http.get<number>(`${this.baseUrl}/entrenador/planes-activos`);
    }

    getPendientesEvaluacion(): Observable<number> {
        return this.http.get<number>(`${this.baseUrl}/entrenador/pendientes-evaluacion`);
    }
}

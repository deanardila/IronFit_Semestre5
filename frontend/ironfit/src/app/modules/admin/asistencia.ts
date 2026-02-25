import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

    export interface ResumenAsistenciaPlan {
    idPlan: number;
    idCliente: number;
    nombreCliente: string;
    nombrePlan: string;
    totalSesionesPlan: number;
    sesionesAsistidas: number;
    porcentajeAsistencia: number;
    sesionesCompletas: number;
    porcentajeCompletas: number;
    }

    @Injectable({
    providedIn: 'root'
    })
    export class AsistenciaService {

    private baseUrl = 'http://localhost:8080/api/asistencias';

    constructor(private http: HttpClient) {}

    obtenerResumenPorEntrenador(idEntrenador: number): Observable<ResumenAsistenciaPlan[]> {
        return this.http.get<ResumenAsistenciaPlan[]>(
        `${this.baseUrl}/entrenador/${idEntrenador}/resumen`
        );
    }

  // luego aquí agregamos el detalle por plan/cliente
}
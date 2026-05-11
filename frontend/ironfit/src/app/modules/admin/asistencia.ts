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

    private baseUrl = 'https://ironfit-backend-production.up.railway.app/api/asistencias';

    constructor(private http: HttpClient) {}

    obtenerResumenPorEntrenador(idEntrenador: number): Observable<ResumenAsistenciaPlan[]> {
        return this.http.get<ResumenAsistenciaPlan[]>(
        `${this.baseUrl}/entrenador/${idEntrenador}/resumen`
        );
    }

}
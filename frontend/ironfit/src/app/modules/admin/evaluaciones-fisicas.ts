import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

    export interface MedidasCorporalesDTO {
    pecho?: number | null;
    cintura?: number | null;
    brazo?: number | null;
    pierna?: number | null;
    }

    export interface EvaluacionFisicaDTO {
    id: string;
    clienteId: string;
    clienteNombre?: string;
    entrenadorId: string;
    entrenadorNombre?: string;
    fecha: string;
    pesoCorporal: number;
    talla: number;
    imc: number;
    porcentajeGraso?: number | null;
    medidasCorporales?: MedidasCorporalesDTO | null;
    observaciones?: string | null;
    fechaCreacion?: string;
    fechaActualizacion?: string;
    }

    export interface EvaluacionFisicaRequestDTO {
    clienteId: string;
    fecha?: string | null;
    pesoCorporal: number | null;
    talla: number | null;
    porcentajeGraso?: number | null;
    pecho?: number | null;
    cintura?: number | null;
    brazo?: number | null;
    pierna?: number | null;
    observaciones?: string | null;
    }

    @Injectable({
    providedIn: 'root'
    })
    export class EvaluacionesFisicasService {

    private readonly apiUrl = 'https://ironfit-backend-production.up.railway.app/api/evaluaciones-fisicas';

    constructor(private http: HttpClient) {}

    listarEvaluaciones(): Observable<EvaluacionFisicaDTO[]> {
        return this.http.get<EvaluacionFisicaDTO[]>(this.apiUrl);
    }

    crearEvaluacion(request: EvaluacionFisicaRequestDTO): Observable<EvaluacionFisicaDTO> {
        return this.http.post<EvaluacionFisicaDTO>(this.apiUrl, request);
    }

    actualizarEvaluacion(id: string, request: EvaluacionFisicaRequestDTO): Observable<EvaluacionFisicaDTO> {
        return this.http.put<EvaluacionFisicaDTO>(`${this.apiUrl}/${id}`, request);
    }

    eliminarEvaluacion(id: string): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    listarMisEvaluaciones(): Observable<EvaluacionFisicaDTO[]> {
        return this.http.get<EvaluacionFisicaDTO[]>(`${this.apiUrl}/mis-evaluaciones`);
    }
}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EjercicioDTO {
    id: string;
    nombre: string;
    descripcion: string;
    categoria: string;
    grupoMuscular: string;
    seriesSugeridas: number | null;
    repeticionesSugeridas: number | null;
    tipoEquipo: string | null;
}

export interface EjercicioCrearDTO {
    nombre: string;
    descripcion: string;
    categoria: string;
    grupoMuscular: string;
    seriesSugeridas: number | null;
    repeticionesSugeridas: number | null;
    tipoEquipo: string | null;
}

@Injectable({
    providedIn: 'root',
})
export class Ejercicios {
    private apiUrl = 'http://localhost:8081/api/ejercicios';

    constructor(private http: HttpClient) {}

    getEjercicios(): Observable<EjercicioDTO[]> {
        return this.http.get<EjercicioDTO[]>(this.apiUrl);
    }

    crearEjercicio(ejercicio: EjercicioCrearDTO): Observable<EjercicioDTO> {
        return this.http.post<EjercicioDTO>(this.apiUrl, ejercicio);
    }

    actualizarEjercicio(id: string, ejercicio: EjercicioCrearDTO): Observable<EjercicioDTO> {
        return this.http.put<EjercicioDTO>(`${this.apiUrl}/${id}`, ejercicio);
    }

    eliminarEjercicio(id: string): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}
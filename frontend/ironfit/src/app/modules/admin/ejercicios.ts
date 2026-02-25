import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

export interface EjercicioDTO{
    idEjercicio: number;
    nombre: string;
    descripcion: string;
    categoria: CategoriaDTO;
    grupoMuscular: GrupoMuscularDTO;
    seriesSugeridas: number;
    repeticionesSugeridas: number;
    tipoEquipo: string;
}

export interface EjercicioCrearDTO {
    nombre: string;
    descripcion: string;
    idCategoria: number;
    idGrupoMuscular: number;
    seriesSugeridas: number;
    repeticionesSugeridas: number;
    tipoEquipo: string;
}

export interface CategoriaDTO {
    idCategoria: number;
    nombre: string;
    estado: boolean;
}

export interface GrupoMuscularDTO {
    idGrupoMuscular: number;
    nombre: string;
    estado: boolean;
}

@Injectable({ providedIn: 'root' })
export class Ejercicios {
    private baseUrl      = 'http://localhost:8080/api/ejercicios';
private categoriasUrl = 'http://localhost:8080/api/ejercicios/categorias';
private gruposUrl     = 'http://localhost:8080/api/ejercicios/grupos-musculares';

    constructor(private http: HttpClient) {}

    // LISTAR EJERCICIOS (DTO)
    getEjercicios(): Observable<EjercicioDTO[]> {
        // Backend expone listado en /api/ejercicios (sin sufijo /ejercicios)
        return this.http.get<EjercicioDTO[]>(`${this.baseUrl}`);
    }

    // CREAR EJERCICIO
    crearEjercicio(dto: EjercicioCrearDTO): Observable<any> {
        // Crear en /api/ejercicios
        return this.http.post(`${this.baseUrl}`, dto);
    }

    // 🔹 LISTAR CATEGORÍAS
    getCategorias(): Observable<CategoriaDTO[]> {
        // Algunas APIs exponen categorías fuera de /ejercicios
        return this.http.get<CategoriaDTO[]>(`${this.categoriasUrl}`);
    }

    // 🔹 LISTAR GRUPOS MUSCULARES
    getGruposMusculares(): Observable<GrupoMuscularDTO[]> {
        return this.http.get<GrupoMuscularDTO[]>(`${this.gruposUrl}`);
    }
}


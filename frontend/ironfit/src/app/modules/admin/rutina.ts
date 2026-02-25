export interface RutinaResumenDTO {
  idRutina: number;
  nombre: string;
  descripcion?: string | null;
  orden?: number | null;
  cantidadEjercicios?: number;
}

export interface RutinasDelPlanDTO {
  idPlan: number;
  nombrePlan?: string | null;
  tieneRutinas: boolean;
  rutinas: RutinaResumenDTO[];
}

import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';

import {
  EntrenamientoRealizadoResponse,
  EntrenamientoRealizadoService
} from '../../../admin/entrenamiento-realizado';

import {
  PlanEntrenamientoDTO,
  Planes
} from '../../../admin/planes';

import {
  RutinaDTO,
  RutinaService
} from '../../../admin/rutina';

interface DiaSemanaVista {
  etiqueta: string;
  fecha: Date;
  entrenado: boolean;
  esHoy: boolean;
  futuro: boolean;
}

interface HistorialAsistenciaVista {
  titulo: string;
  fechaTexto: string;
  detalle: string;
  duracionTexto: string;
  estado: string;
}

@Component({
  selector: 'app-mis-asistencias',
  standalone: false,
  templateUrl: './mis-asistencias.html',
  styleUrl: './mis-asistencias.scss',
})
export class MisAsistencias implements OnInit {

  cargando = false;
  error = '';

  planActivo: PlanEntrenamientoDTO | null = null;
  rutinasPlan: RutinaDTO[] = [];
  entrenamientos: EntrenamientoRealizadoResponse[] = [];

  sesionesTotales = 0;
  sesionesEsperadas = 0;
  porcentajeAsistencia = 0;
  rachaActualSemana = 0;
  mensajeAsistencia = 'Sin registros';
  textoResumenAsistencia = '';
  diasSemana: DiaSemanaVista[] = [];
  historialReciente: HistorialAsistenciaVista[] = [];

  constructor(
    private entrenamientoService: EntrenamientoRealizadoService,
    private planesService: Planes,
    private rutinaService: RutinaService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarMisAsistencias();
  }

  cargarMisAsistencias(): void {
    this.cargando = true;
    this.error = '';

    this.planActivo = null;
    this.rutinasPlan = [];
    this.entrenamientos = [];

    this.planesService.getPlanes().pipe(
      switchMap((planes) => {
        const listaPlanes = planes || [];

        this.planActivo =
          listaPlanes.find(plan => plan.activo) ||
          listaPlanes[0] ||
          null;

        if (!this.planActivo) {
          return forkJoin({
            entrenamientos: this.entrenamientoService.listarMisEntrenamientos(),
            rutinas: of([] as RutinaDTO[])
          });
        }

        return forkJoin({
          entrenamientos: this.entrenamientoService.listarMisEntrenamientos(),
          rutinas: this.rutinaService.getRutinasPorPlan(this.planActivo.id).pipe(
            catchError((err) => {
              console.error('Error cargando rutinas del plan', err);
              return of([] as RutinaDTO[]);
            })
          )
        });
      })
    ).subscribe({
      next: (respuesta) => {
        try {
          this.entrenamientos = respuesta.entrenamientos || [];
          this.rutinasPlan = respuesta.rutinas || [];
          this.construirVista();
        } catch (e) {
          console.error('Error procesando asistencias', e);
          this.error = 'Las asistencias llegaron, pero hubo un problema al mostrarlas.';
        } finally {
          this.cargando = false;
          this.cdr.detectChanges();
        }
      },
      error: (err) => {
        console.error('Error cargando asistencias', err);
        this.error = 'No se pudieron cargar tus asistencias.';
        this.entrenamientos = [];
        this.rutinasPlan = [];
        this.construirVista();
        this.cargando = false;
        this.cdr.detectChanges();
      },
    });
  }

  construirVista(): void {
    const completados = this.entrenamientos.filter(
      item => this.normalizarEstado(item.estado) === 'COMPLETADO'
    );

    // Total de entrenamientos que el cliente sí completó
    this.sesionesTotales = completados.length;

    // Sesiones esperadas según rutinas del plan hasta hoy
    this.sesionesEsperadas = this.calcularSesionesEsperadasHastaHoy();

    // Si hay sesiones esperadas, calcula el porcentaje real
    if (this.sesionesEsperadas > 0) {
      this.porcentajeAsistencia = Math.round(
        (this.sesionesTotales / this.sesionesEsperadas) * 100
      );

      // Evita que pase de 100% si el cliente entrenó más veces de lo esperado
      if (this.porcentajeAsistencia > 100) {
        this.porcentajeAsistencia = 100;
      }

      this.mensajeAsistencia = this.obtenerMensajeAsistencia(this.porcentajeAsistencia);

      this.textoResumenAsistencia =
        `${this.sesionesTotales} de ${this.sesionesEsperadas} esperadas · ${this.mensajeAsistencia}`;
    }

    // Si todavía no hay rutinas esperadas, pero sí hay entrenamientos guardados
    else if (this.sesionesTotales > 0) {
      this.porcentajeAsistencia = 0;
      this.mensajeAsistencia = 'Sin registros';

      this.textoResumenAsistencia =
        `${this.sesionesTotales} sesiones registradas · Rutinas pendientes por asignar`;
    }

    // Si no hay entrenamientos ni rutinas esperadas
    else {
      this.porcentajeAsistencia = 0;
      this.mensajeAsistencia = 'Sin registros';
      this.textoResumenAsistencia = 'Sin registros';
    }

    // Construye los círculos de la semana actual
    this.diasSemana = this.construirSemanaActual(completados);

    // Cuenta cuántos días entrenó esta semana
    this.rachaActualSemana = this.diasSemana.filter(dia => dia.entrenado).length;

    // Construye la lista del historial reciente
    this.historialReciente = this.construirHistorialReciente();
  } 

  calcularSesionesEsperadasHastaHoy(): number {
    if (!this.planActivo || !this.planActivo.fechaInicio) {
      return 0;
    }

    const fechaInicio = this.obtenerFechaSegura(this.planActivo.fechaInicio);
    const hoy = new Date();

    if (!fechaInicio) {
      return 0;
    }

    fechaInicio.setHours(0, 0, 0, 0);
    hoy.setHours(0, 0, 0, 0);

    let fechaFinalCalculo = hoy;

    if (this.planActivo.fechaFin) {
      const fechaFin = this.obtenerFechaSegura(this.planActivo.fechaFin);

      if (fechaFin) {
        fechaFin.setHours(0, 0, 0, 0);
        fechaFinalCalculo = fechaFin < hoy ? fechaFin : hoy;
      }
    }

    if (fechaInicio > fechaFinalCalculo) {
      return 0;
    }

    const diasRutina = this.rutinasPlan
      .filter(rutina => rutina.activo)
      .map(rutina => this.normalizarDiaSemana(rutina.diaSemana))
      .filter(dia => dia >= 0);

    if (diasRutina.length === 0) {
      return 0;
    }

    let totalEsperadas = 0;
    const fechaRecorrido = new Date(fechaInicio);

    while (fechaRecorrido <= fechaFinalCalculo) {
      const diaActual = fechaRecorrido.getDay();

      if (diasRutina.includes(diaActual)) {
        totalEsperadas++;
      }

      fechaRecorrido.setDate(fechaRecorrido.getDate() + 1);
    }

    return totalEsperadas;
  }

  normalizarDiaSemana(diaSemana: string): number {
    const dia = (diaSemana || '')
      .toLowerCase()
      .trim()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '');

    switch (dia) {
      case 'domingo':
        return 0;
      case 'lunes':
        return 1;
      case 'martes':
        return 2;
      case 'miercoles':
        return 3;
      case 'jueves':
        return 4;
      case 'viernes':
        return 5;
      case 'sabado':
        return 6;
      default:
        return -1;
    }
  }

  construirSemanaActual(completados: EntrenamientoRealizadoResponse[]): DiaSemanaVista[] {
    const hoy = new Date();
    const lunes = this.obtenerLunesDeLaSemana(hoy);

    const dias = [
      { etiqueta: 'L', suma: 0 },
      { etiqueta: 'M', suma: 1 },
      { etiqueta: 'X', suma: 2 },
      { etiqueta: 'J', suma: 3 },
      { etiqueta: 'V', suma: 4 },
      { etiqueta: 'S', suma: 5 },
      { etiqueta: 'D', suma: 6 },
    ];

    return dias.map(dia => {
      const fecha = new Date(lunes);
      fecha.setDate(lunes.getDate() + dia.suma);

      const entrenado = completados.some(entrenamiento => {
        const fechaEntrenamiento = this.obtenerFechaSegura(entrenamiento.fecha);
        return fechaEntrenamiento ? this.esMismaFecha(fechaEntrenamiento, fecha) : false;
      });

      return {
        etiqueta: dia.etiqueta,
        fecha,
        entrenado,
        esHoy: this.esMismaFecha(fecha, hoy),
        futuro: fecha > hoy && !this.esMismaFecha(fecha, hoy),
      };
    });
  }

  construirHistorialReciente(): HistorialAsistenciaVista[] {
    return [...this.entrenamientos]
      .sort((a, b) => {
        const fechaA = this.obtenerFechaSegura(a.fecha)?.getTime() || 0;
        const fechaB = this.obtenerFechaSegura(b.fecha)?.getTime() || 0;
        return fechaB - fechaA;
      })
      .slice(0, 6)
      .map(item => {
        const ejercicios = item.ejerciciosRealizados || [];
        const completados = ejercicios.filter(e => e.completado).length;
        const total = ejercicios.length;

        return {
          titulo: item.rutinaNombre || 'Rutina',
          fechaTexto: this.formatearFecha(item.fecha),
          detalle: `${completados || total} ejercicios`,
          duracionTexto: this.estimarDuracion(total),
          estado: this.normalizarEstado(item.estado),
        };
      });
  }

  obtenerLunesDeLaSemana(fecha: Date): Date {
    const nuevaFecha = new Date(fecha);
    const dia = nuevaFecha.getDay();
    const diferencia = dia === 0 ? -6 : 1 - dia;

    nuevaFecha.setDate(nuevaFecha.getDate() + diferencia);
    nuevaFecha.setHours(0, 0, 0, 0);

    return nuevaFecha;
  }

  obtenerFechaSegura(fecha: string): Date | null {
    if (!fecha) {
      return null;
    }

    const fechaConvertida = new Date(fecha);

    if (isNaN(fechaConvertida.getTime())) {
      return null;
    }

    return fechaConvertida;
  }

  esMismaFecha(a: Date, b: Date): boolean {
    return (
      a.getFullYear() === b.getFullYear() &&
      a.getMonth() === b.getMonth() &&
      a.getDate() === b.getDate()
    );
  }

  normalizarEstado(estado: string): string {
    return (estado || '').toUpperCase().trim();
  }

  formatearFecha(fecha: string): string {
    const fechaConvertida = this.obtenerFechaSegura(fecha);

    if (!fechaConvertida) {
      return 'Fecha no disponible';
    }

    return fechaConvertida.toLocaleDateString('es-CO', {
      day: 'numeric',
      month: 'short',
      year: 'numeric',
    });
  }

  estimarDuracion(totalEjercicios: number): string {
    if (!totalEjercicios) {
      return '—';
    }

    const minutos = Math.max(30, totalEjercicios * 12);
    return `${minutos} min`;
  }

  obtenerMensajeAsistencia(porcentaje: number): string {
    if (porcentaje >= 85) return 'Muy buena';
    if (porcentaje >= 70) return 'Buena';
    if (porcentaje >= 50) return 'Regular';
    if (porcentaje > 0) return 'Baja';
    return 'Sin registros';
  }

  actualizar(): void {
    this.cargarMisAsistencias();
  }

  volver(): void {
    this.router.navigate(['/cliente']);
  }
}
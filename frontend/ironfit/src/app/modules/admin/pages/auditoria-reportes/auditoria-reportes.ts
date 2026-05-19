import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { finalize, timeout } from 'rxjs';
import { Router } from '@angular/router';

import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

import {
  AsistenciaReporteDTO,
  AsistenciaService,
  FiltrosAsistencia
} from '../../asistencia';

@Component({
  selector: 'app-auditoria-reportes',
  standalone: false,
  templateUrl: './auditoria-reportes.html',
  styleUrls: ['./auditoria-reportes.scss'],
})
export class AuditoriaReportes implements OnInit {

  resumenes: AsistenciaReporteDTO[] = [];
  resumenesFiltrados: AsistenciaReporteDTO[] = [];

  cargando = false;
  error = '';

  terminoBusqueda = '';
  filtroEstado = 'TODOS';
  fechaInicio = '';
  fechaFin = '';

  totalRegistros = 0;
  totalAsistio = 0;
  totalNoAsistio = 0;
  totalSinRutina = 0;
  porcentajeAsistencia = 0;

  constructor(
    private asistenciaService: AsistenciaService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarResumen();
  }

  cargarResumen(): void {
    this.cargando = true;
    this.error = '';
    this.cdr.detectChanges();

    const filtros: FiltrosAsistencia = {
      fechaInicio: this.fechaInicio || undefined,
      fechaFin: this.fechaFin || undefined,
      estado: this.filtroEstado !== 'TODOS' ? this.filtroEstado : undefined,
    };

    this.asistenciaService.obtenerReporteAsistencias(filtros)
      .pipe(
        timeout(15000),
        finalize(() => {
          this.cargando = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (data) => {
          console.log('Reportes de asistencia recibidos:', data);

          this.resumenes = Array.isArray(data) ? data : [];
          this.aplicarFiltros();
        },
        error: (err) => {
          console.error('Error cargando reportes de asistencia', err);

          this.error = 'Ocurrió un error al cargar las asistencias.';
          this.resumenes = [];
          this.resumenesFiltrados = [];
          this.calcularMetricas();
        }
      });
  }

  aplicarFiltros(): void {
    const b = this.normalizarTexto(this.terminoBusqueda);

    this.resumenesFiltrados = this.resumenes.filter(r => {
      const coincideBusqueda =
        !b ||
        this.normalizarTexto(r.clienteNombre).includes(b) ||
        this.normalizarTexto(r.clienteDocumento).includes(b) ||
        this.normalizarTexto(r.entrenadorNombre).includes(b) ||
        this.normalizarTexto(r.planNombre).includes(b) ||
        this.normalizarTexto(r.rutinaNombre).includes(b) ||
        this.normalizarTexto(r.estadoAsistencia).includes(b) ||
        this.normalizarTexto(r.observaciones).includes(b);

      const coincideEstado =
        this.filtroEstado === 'TODOS' ||
        this.normalizarEstado(r.estadoAsistencia) === this.filtroEstado;

      return coincideBusqueda && coincideEstado;
    });

    this.calcularMetricas();
  }

  calcularMetricas(): void {
    this.totalRegistros = this.resumenesFiltrados.length;

    this.totalAsistio = this.resumenesFiltrados.filter(r =>
      this.normalizarEstado(r.estadoAsistencia) === 'ASISTIO'
    ).length;

    this.totalNoAsistio = this.resumenesFiltrados.filter(r =>
      this.normalizarEstado(r.estadoAsistencia) === 'NO_ASISTIO'
    ).length;

    this.totalSinRutina = this.resumenesFiltrados.filter(r =>
      this.normalizarEstado(r.estadoAsistencia) === 'ASISTIO_SIN_RUTINA'
    ).length;

    this.porcentajeAsistencia = this.totalRegistros > 0
      ? Math.round((this.totalAsistio / this.totalRegistros) * 100)
      : 0;
  }

  claseEstado(estado: string): string {
    const normalizado = this.normalizarEstado(estado);

    if (normalizado === 'ASISTIO') {
      return 'asistio';
    }

    if (normalizado === 'NO_ASISTIO') {
      return 'no-asistio';
    }

    return 'sin-rutina';
  }

  normalizarEstado(estado: string | undefined | null): string {
    return (estado || '')
      .toString()
      .trim()
      .toUpperCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '')
      .replace(/\s+/g, '_');
  }

  normalizarTexto(texto: string | undefined | null): string {
    return (texto || '')
      .toString()
      .trim()
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '');
  }

  limpiarFiltros(): void {
    this.terminoBusqueda = '';
    this.filtroEstado = 'TODOS';
    this.fechaInicio = '';
    this.fechaFin = '';
    this.cargarResumen();
  }

  exportarPDF(): void {
    if (this.resumenesFiltrados.length === 0) {
      alert('No hay registros para exportar.');
      return;
    }

    const doc = new jsPDF({
      orientation: 'landscape',
      unit: 'mm',
      format: 'a4'
    });

    const fechaGeneracion = new Date().toLocaleString('es-CO');

    doc.setFontSize(16);
    doc.text('IRONFIT - Reporte de Asistencias', 14, 15);

    doc.setFontSize(10);
    doc.text(`Fecha de generación: ${fechaGeneracion}`, 14, 22);

    const rango = this.fechaInicio || this.fechaFin
      ? `Rango: ${this.fechaInicio || 'Inicio'} / ${this.fechaFin || 'Hoy'}`
      : 'Rango: Todos los registros';

    doc.text(rango, 14, 28);

    doc.text(`Total registros: ${this.totalRegistros}`, 14, 36);
    doc.text(`Asistieron: ${this.totalAsistio}`, 60, 36);
    doc.text(`No asistieron: ${this.totalNoAsistio}`, 100, 36);
    doc.text(`Asistió sin rutina: ${this.totalSinRutina}`, 145, 36);
    doc.text(`Cumplimiento: ${this.porcentajeAsistencia}%`, 200, 36);

    const filas = this.resumenesFiltrados.map(r => [
      r.fechaAsistencia || '-',
      r.clienteNombre || '-',
      r.clienteDocumento || '-',
      r.entrenadorNombre || '-',
      r.planNombre || '-',
      r.rutinaNombre || '-',
      r.estadoAsistencia || '-',
      r.observaciones || '-'
    ]);

    autoTable(doc, {
      startY: 44,
      head: [[
        'Fecha',
        'Cliente',
        'Documento',
        'Entrenador',
        'Plan',
        'Rutina',
        'Estado',
        'Observaciones'
      ]],
      body: filas,
      styles: {
        fontSize: 8,
        cellPadding: 2
      },
      headStyles: {
        fillColor: [255, 138, 31],
        textColor: [20, 20, 20]
      },
      alternateRowStyles: {
        fillColor: [245, 245, 245]
      }
    });

    doc.save('reporte-asistencias-ironfit.pdf');
  }

  actualizar(): void {
    this.cargarResumen();
  }

  volverAlDashboard(): void {
    this.router.navigate(['/admin']);
  }
}
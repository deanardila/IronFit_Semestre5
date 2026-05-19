import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { finalize, timeout } from 'rxjs';
import { AsistenciaReporteDTO, AsistenciaService } from '../../../admin/asistencia';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-asistencias',
  standalone: false,
  templateUrl: './asistencias.html',
  styleUrls: ['./asistencias.scss'],
})
export class Asistencias implements OnInit {

  asistencias: AsistenciaReporteDTO[] = [];
  asistenciasFiltradas: AsistenciaReporteDTO[] = [];

  cargando = false;
  error: string | null = null;

  terminoBusqueda = '';
  filtroEstado = 'TODOS';

  totalRegistros = 0;
  totalAsistio = 0;
  totalNoAsistio = 0;
  porcentajeCumplimiento = 0;

  constructor(
    private asistenciaApi: AsistenciaService,
    private router: Router,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const buscar = params['buscar'];
      if (buscar) {
        this.terminoBusqueda = buscar;
      }

      this.cargarAsistencias();
    });
  }

  cargarAsistencias(): void {
    this.cargando = true;
    this.error = null;
    this.cdr.detectChanges();

    this.asistenciaApi.obtenerReporteAsistencias()
      .pipe(
        timeout(15000),
        finalize(() => {
          this.cargando = false;
          this.cdr.detectChanges();
        })
      )
      .subscribe({
        next: (lista) => {
          console.log('Asistencias recibidas:', lista);

          this.asistencias = Array.isArray(lista) ? lista : [];
          this.aplicarFiltros();
        },
        error: (err) => {
          console.error('Error cargando asistencias', err);

          this.error = 'No se pudieron cargar las asistencias.';
          this.asistencias = [];
          this.asistenciasFiltradas = [];
          this.calcularMetricas();
        }
      });
  }

  aplicarFiltros(): void {
    const b = this.normalizarTexto(this.terminoBusqueda);

    this.asistenciasFiltradas = this.asistencias.filter(a => {
      const coincideBusqueda =
        !b ||
        this.normalizarTexto(a.clienteNombre).includes(b) ||
        this.normalizarTexto(a.clienteDocumento).includes(b) ||
        this.normalizarTexto(a.planNombre).includes(b) ||
        this.normalizarTexto(a.rutinaNombre).includes(b) ||
        this.normalizarTexto(a.estadoAsistencia).includes(b);

      const coincideEstado =
        this.filtroEstado === 'TODOS' ||
        this.normalizarEstado(a.estadoAsistencia) === this.filtroEstado;

      return coincideBusqueda && coincideEstado;
    });

    this.calcularMetricas();
  }

  calcularMetricas(): void {
    this.totalRegistros = this.asistenciasFiltradas.length;

    this.totalAsistio = this.asistenciasFiltradas.filter(a =>
      this.normalizarEstado(a.estadoAsistencia) === 'ASISTIO'
    ).length;

    this.totalNoAsistio = this.asistenciasFiltradas.filter(a =>
      this.normalizarEstado(a.estadoAsistencia) === 'NO_ASISTIO'
    ).length;

    this.porcentajeCumplimiento = this.totalRegistros > 0
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

  actualizar(): void {
    this.cargarAsistencias();
  }

  volver(): void {
    this.router.navigate(['/entrenador']);
  }
}
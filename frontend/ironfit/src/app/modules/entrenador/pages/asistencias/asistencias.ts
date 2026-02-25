import { Component, OnInit } from '@angular/core';
import { AsistenciaService, ResumenAsistenciaPlan } from '../../../admin/asistencia';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-asistencias',
  standalone: false,
  templateUrl: './asistencias.html',
  styleUrls: ['./asistencias.scss'],
})
export class Asistencias implements OnInit {
  resumen: ResumenAsistenciaPlan[] = [];
  asistenciasFiltradas: ResumenAsistenciaPlan[] = [];
  cargando = false;
  terminoBusqueda = '';
  idEntrenador: number = Number(localStorage.getItem('idEntrenador')) || 0;
  error: string | null = null;

  constructor(private asistenciaApi: AsistenciaService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const buscar = params['buscar'];
      if (buscar) { this.terminoBusqueda = buscar; }
      this.cargarResumen();
    });
  }

  cargarResumen(): void {
    if (!this.idEntrenador) { this.error = 'ID de entrenador no disponible'; return; }
    this.cargando = true; this.error = null;
    this.asistenciaApi.obtenerResumenPorEntrenador(this.idEntrenador).subscribe({
      next: lista => { this.resumen = lista || []; this.aplicarFiltros(); this.cargando = false; },
      error: err => { console.error('Error cargando asistencias', err); this.error = 'No se pudo cargar el resumen.'; this.resumen = []; this.asistenciasFiltradas = []; this.cargando = false; }
    });
  }

  aplicarFiltros(): void {
    const b = this.terminoBusqueda.trim().toLowerCase();
    if (!b) { this.asistenciasFiltradas = [...this.resumen]; return; }
    this.asistenciasFiltradas = this.resumen.filter(r =>
      r.nombreCliente.toLowerCase().includes(b) ||
      r.nombrePlan.toLowerCase().includes(b)
    );
  }

  actualizar(): void { this.cargarResumen(); }
  volver(): void { this.router.navigate(['/entrenador']); }
}

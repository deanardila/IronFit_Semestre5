import { Component, OnInit } from '@angular/core';
import { Planes, PlanResumen } from '../../../admin/planes';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-mis-planes',
  standalone: false,
  templateUrl: './mis-planes.html',
  styleUrls: ['./mis-planes.scss'],
})
export class MisPlanes implements OnInit {
  planes: PlanResumen[] = [];
  planesFiltrados: PlanResumen[] = [];
  cargando = false;
  terminoBusqueda = '';
  idEntrenador: number = Number(localStorage.getItem('idEntrenador')) || 0;

  constructor(private planesApi: Planes, private router: Router, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const buscar = params['buscar'];
      if (buscar) { this.terminoBusqueda = buscar; }
      this.cargarPlanes();
    });
  }

  cargarPlanes(): void {
    this.cargando = true;
    const origen = this.idEntrenador ? this.planesApi.getPlanesPorEntrenador(this.idEntrenador) : this.planesApi.getResumenPlanes();
    origen.subscribe({
      next: lista => {
        // Solo planes activos (estado ACTIVO)
        this.planes = (lista || []).filter(p => (p.estado || '').toUpperCase().includes('ACTIVO'));
        this.aplicarFiltros();
        this.cargando = false;
      },
      error: err => {
        console.error('Error cargando planes', err);
        this.planes = [];
        this.planesFiltrados = [];
        this.cargando = false;
      }
    });
  }

  aplicarFiltros(): void {
    const b = this.terminoBusqueda.trim().toLowerCase();
    if (!b) { this.planesFiltrados = [...this.planes]; return; }
    this.planesFiltrados = this.planes.filter(p =>
      p.nombrePlan.toLowerCase().includes(b) ||
      (p.nombreCliente || '').toLowerCase().includes(b) ||
      (p.nombreEntrenador || '').toLowerCase().includes(b)
    );
  }

  volver(): void { this.router.navigate(['/entrenador']); }
  actualizar(): void { this.cargarPlanes(); }
}

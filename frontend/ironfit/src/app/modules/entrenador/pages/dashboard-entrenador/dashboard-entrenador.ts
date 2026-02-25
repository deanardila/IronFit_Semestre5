import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Dashboard } from '../../../admin/dashboard';

@Component({
  selector: 'app-dashboard-entrenador',
  standalone: false,
  templateUrl: './dashboard-entrenador.html',
  styleUrls: ['./dashboard-entrenador.scss'],
})
export class DashboardEntrenador implements OnInit {

  // Observables para las tarjetas
  clientesActivos$!: Observable<number>;
  sesionesEstesMes$!: Observable<number>;
  planesActivos$!: Observable<number>;
  pendientesEvaluacion$!: Observable<number>;

  // Estado del menú hamburguesa
  showMenu: boolean = false;

  // nombre del usuario logueado
  nombreUsuario: string = '';

  constructor(
    private router: Router,
    private dashboard: Dashboard
  ) {}

  ngOnInit(): void {
    // Leer nombre guardado en localStorage
    this.nombreUsuario = localStorage.getItem('nombreUsuario') ?? 'Entrenador';
    console.log('Nombre leído en DashboardEntrenador:', this.nombreUsuario);
    this.clientesActivos$ = this.dashboard.getClientesActivos();
    this.sesionesEstesMes$ = this.dashboard.getSesionesEsteMes();
    this.planesActivos$ = this.dashboard.getPlanesActivos();
    this.pendientesEvaluacion$ = this.dashboard.getPendientesEvaluacion();
  }

  // --- Menú hamburguesa ---
  toggleMenu(): void {
    this.showMenu = !this.showMenu;
  }

  closeMenu(): void {
    this.showMenu = false;
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('rol');
    localStorage.removeItem('nombreUsuario');
    this.router.navigate(['/login']);
  }

  // Tarjeta "CLIENTES ACTIVOS"
  onClientesActivosClick(): void {
    this.router.navigate(['/entrenador/MisClientes'], { queryParams: { origen: 'dashboard' } });
  }

  // Tarjeta "SESIONES REALIZADAS"
  onSesionesClick(): void {
    this.router.navigate(['/entrenador/asistencias'], { queryParams: { origen: 'dashboard' } });
  }

  // Tarjeta "PLANES ACTIVOS"
  onPlanesActivosClick(): void {
    this.router.navigate(['/entrenador/MisPlanes'], { queryParams: { origen: 'dashboard' } });
  }

  // Tarjeta "PENDIENTES DE EVALUACIÓN"
  onPendientesClick(): void {
    this.router.navigate(['/entrenador/evaluaciones']);
  }
}

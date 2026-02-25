import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Dashboard } from '../../../admin/dashboard';

@Component({
  selector: 'app-dashboard-cliente',
  standalone: false,
  templateUrl: './dashboard-cliente.html',
  styleUrl: './dashboard-cliente.scss',
})
export class DashboardCliente implements OnInit {

  // Observables para las tarjetas
  planActual$!: Observable<string>;
  rutinasActivas$!: Observable<number>;
  asistenciasEsteMes$!: Observable<number>;
  progresoGeneral$!: Observable<string>;

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
    this.nombreUsuario = localStorage.getItem('nombreUsuario') ?? 'Cliente';
    console.log('Nombre leído en DashboardCliente:', this.nombreUsuario);
    this.planActual$ = this.dashboard.getPlanActual();
    this.rutinasActivas$ = this.dashboard.getRutinasActivas();
    this.asistenciasEsteMes$ = this.dashboard.getAsistenciasEsteMes();
    this.progresoGeneral$ = this.dashboard.getProgresoGeneral();
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

  // Tarjeta "PLAN ACTUAL"
  onPlanActualClick(): void {
    this.router.navigate(['/cliente/plan']);
  }

  // Tarjeta "RUTINAS ACTIVAS"
  onRutinasClick(): void {
    this.router.navigate(['/cliente/MisRutinas']);
  }

  // Tarjeta "MIS ASISTENCIAS"
  onAsistenciasClick(): void {
    this.router.navigate(['/cliente/asistencias']);
  }

  // Tarjeta "PROGRESO GENERAL"
  onProgresoClick(): void {
    this.router.navigate(['/cliente/evaluaciones']);
  }
}

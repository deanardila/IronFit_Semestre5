import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Dashboard } from '../../dashboard';

@Component({
  selector: 'app-dashboard-admin',
  standalone: false,
  templateUrl: './dashboard-admin.html',
  styleUrl: './dashboard-admin.scss',
})
export class DashboardAdmin implements OnInit {

  // Observables para las tarjetas
  clientesActivos$!: Observable<number>;
  entrenadoresActivos$!: Observable<number>;
  ejerciciosRegistrados$!: Observable<number>;
  auditoriasMes$!: Observable<number>;

  // Estado del menú hamburguesa
  showMenu: boolean = false;

    //nombre del usuario logueado
  nombreUsuario: string = ''; 

  constructor(
    private router: Router,
    private dashboard: Dashboard
  ) {}

  ngOnInit(): void {
    // Leer nombre guardado en localStorage
    this.nombreUsuario = localStorage.getItem('nombreUsuario') ?? 'Admin';
    console.log('Nombre leído en DashboardAdmin:', this.nombreUsuario);
    this.clientesActivos$ = this.dashboard.getClientesActivos();
    this.entrenadoresActivos$ = this.dashboard.getEntrenadoresActivos();
    this.ejerciciosRegistrados$ = this.dashboard.getEjerciciosRegistrados();
    this.auditoriasMes$ = this.dashboard.getAuditoriasMes();
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
    this.router.navigate(
  ['/admin/usuarios'],
  { queryParams: { tipo: 'cliente', estado: 'ACTIVO' } }
);
  }

  //  Tarjeta "ENTRENADORES"
  onEntrenadoresActivosClick(): void {
    this.router.navigate(['/admin/usuarios'], {
      queryParams: {
        tipo: 'entrenador',
        estado: 'ACTIVO'
      }
    });
  }

  // Click en EJERCICIOS
  onEjerciciosClick(): void {
    this.router.navigate(['/admin/ejercicios'], {
      queryParams: { registrados: 'SI' }
    });
  }

  // Click en AUDITORÍAS
  onAuditoriasClick(): void {
    this.router.navigate(['/admin/auditoria']);
  }

  
}

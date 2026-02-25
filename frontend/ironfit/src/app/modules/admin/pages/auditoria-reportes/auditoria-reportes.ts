import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { AsistenciaService, ResumenAsistenciaPlan } from '../../asistencia';

@Component({
  selector: 'app-auditoria-reportes',
  standalone: false,
  templateUrl: './auditoria-reportes.html',
  styleUrl: './auditoria-reportes.scss',
})
export class AuditoriaReportes implements OnInit {
  resumenes: ResumenAsistenciaPlan[] = [];
  resumenesFiltrados: ResumenAsistenciaPlan[] = [];
  cargando = false;
  error: string | null = null;

  idEntrenador = 2;
  
  // Búsqueda por cédula o nombre
  terminoBusqueda: string = '';

  constructor(
    private asistenciaService: AsistenciaService,
    private cdr: ChangeDetectorRef,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.cargarResumen();
  }

  cargarResumen(): void {
    this.error = null;

    this.asistenciaService.obtenerResumenPorEntrenador(this.idEntrenador)
      .subscribe({
        next: (data: ResumenAsistenciaPlan[]) => {
          
          this.resumenes = data;
          this.aplicarFiltros();
          this.cargando = false;
          

          // Forzar detección de cambios
          this.cdr.detectChanges();
          console.log('🔄 Detección de cambios forzada');
        },
        error: (err: any) => {
          console.error('❌ Error cargando resumen de asistencia:', err);
          console.error('📋 Detalles del error:', {
            status: err.status,
            message: err.message,
            url: err.url
          });
          this.error = 'Ocurrió un error al cargar las asistencias.';
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
  }

  aplicarFiltros(): void {
    const busqueda = this.terminoBusqueda.toLowerCase().trim();
    
    if (!busqueda) {
      this.resumenesFiltrados = [...this.resumenes];
      return;
    }
    
    this.resumenesFiltrados = this.resumenes.filter(r => {
      const nombreCliente = (r.nombreCliente || '').toLowerCase();
      const nombrePlan = (r.nombrePlan || '').toLowerCase();
      
      return nombreCliente.includes(busqueda) || nombrePlan.includes(busqueda);
    });
    
    this.cdr.detectChanges();
  }

  volverAlDashboard(): void {
    this.router.navigate(['/admin']);
  }


}

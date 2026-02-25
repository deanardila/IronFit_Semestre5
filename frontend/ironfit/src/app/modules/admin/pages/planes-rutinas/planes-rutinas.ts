import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Planes, PlanResumen } from '../../planes';

// Si ya tienes RutinaDTO definido en otro lado, puedes importarlo.
// De momento lo declaro aquí simple:
export interface RutinaDTO {
  idRutina: number;
  nombre: string;
  descripcion?: string;
}

@Component({
  selector: 'app-planes-rutinas',
  standalone: false,
  templateUrl: './planes-rutinas.html',
  styleUrls: ['./planes-rutinas.scss'],   // 👈 styleUrls (en plural)
})
export class PlanesRutinas implements OnInit {

  planes: PlanResumen[] = [];
  planesFiltrados: PlanResumen[] = [];
  rutinas: RutinaDTO[] = [];
  rutinasFiltradas: RutinaDTO[] = [];

  idPlanSeleccionado: number | null = null;

  cargando: boolean = false;          // para mostrar "Cargando..."
  error: string | null = null;
  
  // Búsqueda por nombre
  terminoBusqueda: string = '';

  showingRutinas: boolean = false;    // si estás mostrando la tabla de rutinas

  constructor(
    private planesApi: Planes,
    private cdr: ChangeDetectorRef,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');

    if (idParam) {
      const idPlan = Number(idParam);
      if (!isNaN(idPlan)) {
        this.idPlanSeleccionado = idPlan;
        this.cargarRutinas(idPlan);
        return;
      }
    }

    // Si no viene id en la ruta, mostramos primero la lista de planes
    this.cargarPlanes();
  }

  // 🔹 Cargar lista de planes (resumen)
  cargarPlanes(): void {
    this.cargando = true;
    this.error = null;

    this.planesApi.getResumenPlanes().subscribe({
      next: (lista: PlanResumen[]) => {
        console.log('Planes recibidos:', lista);
        this.planes = lista ?? [];

        // Extraer el nombre del estado si viene como objeto
        this.planes = this.planes.map(p => ({
          ...p,
          estado: typeof p.estado === 'string'
            ? p.estado
            : (p as any).estado?.name || 'Desconocido'
        }));

        console.log('Planes asignados al componente:', this.planes);
        this.aplicarFiltrosPlanes();
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: (err: any) => {
        console.error('Error cargando planes', err);
        this.planes = [];
        this.error = 'Ocurrió un error al cargar los planes.';
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  volverAlDashboard(): void {
    this.router.navigate(['/admin']);
  }

  // Ir desde la lista de planes a la vista de rutinas
  verRutina(idPlan: number): void {
    this.router.navigate(['/planes', idPlan, 'rutina']);
  }

  // 🔹 Cargar rutinas de un plan (simple, SIN JSON.parse raro)
  cargarRutinas(idPlan: number): void {
  this.showingRutinas = true;
  this.cargando = true;

  this.planesApi.getRutinasPorPlan(idPlan).subscribe({
    next: (lista) => {
      console.log('Rutinas recibidas (JSON):', lista);
      this.rutinas = Array.isArray(lista) ? lista : [];
      this.aplicarFiltrosRutinas();
      this.cargando = false;
      this.cdr.detectChanges();
    },
    error: (err) => {
      console.error('Error cargando rutinas', err);
      this.rutinas = [];
      this.cargando = false;
      this.cdr.detectChanges();
    }
  });
  }

  aplicarFiltrosPlanes(): void {
    const busqueda = this.terminoBusqueda.toLowerCase().trim();
    
    if (!busqueda) {
      this.planesFiltrados = [...this.planes];
      return;
    }
    
    this.planesFiltrados = this.planes.filter(p => {
      const nombrePlan = (p.nombrePlan || '').toLowerCase();
      const nombreCliente = (p.nombreCliente || '').toLowerCase();
      const nombreEntrenador = (p.nombreEntrenador || '').toLowerCase();
      
      return nombrePlan.includes(busqueda) || 
             nombreCliente.includes(busqueda) ||
             nombreEntrenador.includes(busqueda);
    });
    
    this.cdr.detectChanges();
  }

  aplicarFiltrosRutinas(): void {
    const busqueda = this.terminoBusqueda.toLowerCase().trim();
    
    if (!busqueda) {
      this.rutinasFiltradas = [...this.rutinas];
      return;
    }
    
    this.rutinasFiltradas = this.rutinas.filter(r => {
      const nombreRutina = (r.nombre || '').toLowerCase();
      const descripcion = (r.descripcion || '').toLowerCase();
      
      return nombreRutina.includes(busqueda) || descripcion.includes(busqueda);
    });
    
    this.cdr.detectChanges();
  }

  volverAListaPlanes(): void {
    this.showingRutinas = false;
    this.terminoBusqueda = '';
    this.router.navigate(['/admin/planes']);
  }
}

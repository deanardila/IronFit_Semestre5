import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { PlanEntrenamientoDTO, PlanEntrenamientoCrearDTO, Planes } from '../../../admin/planes';

@Component({
  selector: 'app-mis-planes',
  standalone: false,
  templateUrl: './mis-planes.html',
  styleUrls: ['./mis-planes.scss'],
})
export class MisPlanes implements OnInit {
  planes: PlanEntrenamientoDTO[] = [];
  planesFiltrados: PlanEntrenamientoDTO[] = [];
  cargando = false;
  error: string | null = null;

  mostrarFormularioCrear = false;
  modoEdicion = false;
  idPlanEditando: string | null = null;

  terminoBusqueda = '';
  terminoBusquedaCliente = '';

  entrenadorId = '';
  clientes: any[] = [];
  clienteSeleccionado: any = null;

  formulario: PlanEntrenamientoCrearDTO = {
    nombre: '',
    descripcion: '',
    objetivo: '',
    fechaInicio: '',
    fechaFin: '',
    clienteId: '',
    entrenadorId: '',
  };

  constructor(
    private planesService: Planes,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.obtenerUsuarioActual();
    this.cargarPlanes();
  }

  cargarPlanes(): void {
    this.cargando = true;
    this.error = null;

    this.planesService.getPlanes().subscribe({
      next: (data: PlanEntrenamientoDTO[]) => {
        this.planes = data || [];
        this.aplicarFiltros();
        this.cargando = false;
      },
      error: (err: any) => {
        console.error('Error cargando planes', err);
        this.error = 'No se pudieron cargar los planes.';
        this.cargando = false;
      }
    });
  }

  obtenerUsuarioActual(): void {
    this.http.get<any>('http://localhost:8081/api/usuarios/me').subscribe({
      next: (res) => {
        this.entrenadorId = res.id;
      },
      error: (err) => {
        console.error('Error obteniendo usuario actual', err);
        this.error = 'No se pudo obtener el usuario actual.';
      }
    });
  }

  toggleFormularioCrear(): void {
    if (this.mostrarFormularioCrear || this.modoEdicion) {
      this.cancelarFormulario();
      return;
    }

    this.mostrarFormularioCrear = true;
    this.modoEdicion = false;
  }

  aplicarFiltros(): void {
    const texto = this.terminoBusqueda.trim().toLowerCase();

    if (!texto) {
      this.planesFiltrados = [...this.planes];
      return;
    }

    this.planesFiltrados = this.planes.filter((plan) => {
      const nombre = plan.nombre?.toLowerCase() || '';
      const cliente = plan.clienteId?.toLowerCase() || '';
      const entrenador = plan.entrenadorId?.toLowerCase() || '';
      const objetivo = plan.objetivo?.toLowerCase() || '';

      return (
        nombre.includes(texto) ||
        cliente.includes(texto) ||
        entrenador.includes(texto) ||
        objetivo.includes(texto)
      );
    });
  }

  buscarClientes(): void {
    const texto = this.terminoBusquedaCliente.trim();

    if (texto.length < 2) {
      this.clientes = [];
      return;
    }

    this.http
      .get<any[]>(`http://localhost:8081/api/usuarios/clientes/buscar?texto=${encodeURIComponent(texto)}`)
      .subscribe({
        next: (data: any[]) => {
          this.clientes = data || [];
        },
        error: (err: any) => {
          console.error('Error buscando clientes', err);
          this.clientes = [];
        }
      });
  }

  seleccionarCliente(cliente: any): void {
    this.clienteSeleccionado = cliente;
    this.formulario.clienteId = cliente.id;
    this.terminoBusquedaCliente =
      `${cliente.nombres || ''} ${cliente.apellidos || ''} - ${cliente.numDoc || ''}`.trim();
    this.clientes = [];
  }

  guardarPlan(): void {
    this.error = null;

    if (!this.entrenadorId) {
      this.error = 'No se pudo identificar el entrenador actual.';
      return;
    }

    if (!this.formulario.clienteId) {
      this.error = 'Debes seleccionar un cliente.';
      return;
    }

    this.formulario.entrenadorId = this.entrenadorId;

    if (this.modoEdicion && this.idPlanEditando) {
      this.planesService.actualizarPlan(this.idPlanEditando, this.formulario).subscribe({
        next: () => {
          this.resetFormulario();
          this.cargarPlanes();
        },
        error: (err: any) => {
          console.error('Error actualizando plan', err);
          this.error = err?.error?.message || 'No se pudo actualizar el plan.';
        }
      });
      return;
    }

    this.planesService.crearPlan(this.formulario).subscribe({
      next: () => {
        this.resetFormulario();
        this.cargarPlanes();
      },
      error: (err: any) => {
        console.error('Error creando plan', err);
        this.error = err?.error?.message || 'No se pudo crear el plan.';
      }
    });
  }

  editarPlan(plan: PlanEntrenamientoDTO): void {
    this.modoEdicion = true;
    this.mostrarFormularioCrear = true;
    this.idPlanEditando = plan.id;

    this.formulario = {
      nombre: plan.nombre,
      descripcion: plan.descripcion,
      objetivo: plan.objetivo,
      fechaInicio: plan.fechaInicio,
      fechaFin: plan.fechaFin,
      clienteId: plan.clienteId,
      entrenadorId: plan.entrenadorId,
    };

    this.clienteSeleccionado = {
      id: plan.clienteId,
      nombres: '',
      apellidos: '',
      numDoc: ''
    };

    this.terminoBusquedaCliente = plan.clienteId;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  cambiarEstado(plan: PlanEntrenamientoDTO): void {
    const nuevoEstado = !plan.activo;

    this.planesService.cambiarEstado(plan.id, nuevoEstado).subscribe({
      next: () => {
        this.cargarPlanes();
      },
      error: (err: any) => {
        console.error('Error cambiando estado del plan', err);
        this.error = err?.error?.message || 'No se pudo cambiar el estado del plan.';
      }
    });
  }

  cancelarFormulario(): void {
    this.resetFormulario();
  }

  resetFormulario(): void {
    this.modoEdicion = false;
    this.mostrarFormularioCrear = false;
    this.idPlanEditando = null;

    this.terminoBusquedaCliente = '';
    this.clientes = [];
    this.clienteSeleccionado = null;

    this.formulario = {
      nombre: '',
      descripcion: '',
      objetivo: '',
      fechaInicio: '',
      fechaFin: '',
      clienteId: '',
      entrenadorId: '',
    };
  }

  volver(): void {
    this.router.navigate(['/entrenador']);
  }

  actualizar(): void {
    this.cargarPlanes();
  }
}
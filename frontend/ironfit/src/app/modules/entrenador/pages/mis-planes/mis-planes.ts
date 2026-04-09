import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { timeout } from 'rxjs';
import { PlanEntrenamientoDTO, PlanEntrenamientoCrearDTO, Planes } from '../../../admin/planes';

@Component({
  selector: 'app-mis-planes',
  standalone: false,
  templateUrl: './mis-planes.html',
  styleUrls: ['./mis-planes.scss'],
})
export class MisPlanes implements OnInit {
  private readonly requestTimeoutMs = 15000;

  planes: PlanEntrenamientoDTO[] = [];
  planesFiltrados: PlanEntrenamientoDTO[] = [];

  cargando = false;
  guardando = false;
  cambiandoEstadoId: string | null = null;
  error: string | null = null;

  mostrarFormularioCrear = false;
  modoEdicion = false;
  idPlanEditando: string | null = null;

  terminoBusqueda = '';
  terminoBusquedaCliente = '';

  entrenadorId = '';
  clientes: any[] = [];
  clienteSeleccionado: any = null;

  filtroEstado: 'TODOS' | 'ACTIVOS' | 'INACTIVOS' = 'TODOS';

  formulario: PlanEntrenamientoCrearDTO = this.crearFormularioVacio();

  constructor(
    private planesService: Planes,
    private http: HttpClient,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.inicializarModulo();
  }

  inicializarModulo(): void {
    this.cargando = true;
    this.error = null;

    this.http.get<any>('http://localhost:8081/api/usuarios/me')
      .pipe(timeout(this.requestTimeoutMs))
      .subscribe({
        next: (res) => {
          this.entrenadorId = res?.id || '';
          this.cargarPlanes();
        },
        error: (err) => {
          console.error('Error obteniendo usuario actual', err);
          this.error = this.obtenerMensajeError(err, 'No se pudo obtener el usuario actual.');
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
  }

  cargarPlanes(): void {
    this.cargando = true;
    this.error = null;

    this.planesService.getPlanes()
      .pipe(timeout(this.requestTimeoutMs))
      .subscribe({
        next: (data: any) => {
          this.planes = this.normalizarPlanes(data);
          this.aplicarFiltros();
          this.cargando = false;
          this.cdr.detectChanges();
        },
        error: (err: any) => {
          console.error('Error cargando planes', err);
          this.error = this.obtenerMensajeError(err, 'No se pudieron cargar los planes.');
          this.cargando = false;
          this.cdr.detectChanges();
        }
      });
  }

  toggleFormularioCrear(): void {
    if (this.guardando) {
      return;
    }

    if (this.mostrarFormularioCrear || this.modoEdicion) {
      this.cancelarFormulario();
      return;
    }

    this.mostrarFormularioCrear = true;
    this.modoEdicion = false;
    this.error = null;
    this.cdr.detectChanges();
  }

  aplicarFiltros(): void {
    const texto = this.terminoBusqueda.trim().toLowerCase();

    let resultado = [...this.planes];

    if (this.filtroEstado === 'ACTIVOS') {
      resultado = resultado.filter(plan => this.normalizarActivo(plan.activo) === true);
    }

    if (this.filtroEstado === 'INACTIVOS') {
      resultado = resultado.filter(plan => this.normalizarActivo(plan.activo) === false);
    }

    if (texto) {
      resultado = resultado.filter((plan) => {
        const nombre = plan.nombre?.toLowerCase() || '';
        const cliente = plan.clienteNombre?.toLowerCase() || plan.clienteId?.toLowerCase() || '';
        const entrenador = plan.entrenadorNombre?.toLowerCase() || plan.entrenadorId?.toLowerCase() || '';
        const objetivo = plan.objetivo?.toLowerCase() || '';
        const descripcion = plan.descripcion?.toLowerCase() || '';

        return (
          nombre.includes(texto) ||
          cliente.includes(texto) ||
          entrenador.includes(texto) ||
          objetivo.includes(texto) ||
          descripcion.includes(texto)
        );
      });
    }

    this.planesFiltrados = resultado;
    this.cdr.detectChanges();
  }

  buscarClientes(): void {
    const texto = this.terminoBusquedaCliente.trim();

    if (texto.length < 2) {
      this.clientes = [];
      this.cdr.detectChanges();
      return;
    }

    this.http
      .get<any[]>(`http://localhost:8081/api/usuarios/clientes/buscar?texto=${encodeURIComponent(texto)}`)
      .pipe(timeout(this.requestTimeoutMs))
      .subscribe({
        next: (data: any[]) => {
          this.clientes = data || [];
          this.cdr.detectChanges();
        },
        error: (err: any) => {
          console.error('Error buscando clientes', err);
          this.clientes = [];
          this.cdr.detectChanges();
        }
      });
  }

  seleccionarCliente(cliente: any): void {
    this.clienteSeleccionado = cliente;
    this.formulario.clienteId = cliente.id;
    this.terminoBusquedaCliente = this.construirNombreCliente(cliente);
    this.clientes = [];
    this.error = null;
    this.cdr.detectChanges();
  }

  guardarPlan(): void {
    if (this.guardando) {
      return;
    }

    this.error = null;

    if (!this.entrenadorId) {
      this.error = 'No se pudo identificar el entrenador actual.';
      this.cdr.detectChanges();
      return;
    }

    if (!this.formulario.nombre?.trim()) {
      this.error = 'El nombre del plan es obligatorio.';
      this.cdr.detectChanges();
      return;
    }

    if (!this.formulario.descripcion?.trim()) {
      this.error = 'La descripción del plan es obligatoria.';
      this.cdr.detectChanges();
      return;
    }

    if (!this.formulario.objetivo?.trim()) {
      this.error = 'El objetivo del plan es obligatorio.';
      this.cdr.detectChanges();
      return;
    }

    if (!this.formulario.fechaInicio) {
      this.error = 'La fecha de inicio es obligatoria.';
      this.cdr.detectChanges();
      return;
    }

    if (!this.formulario.fechaFin) {
      this.error = 'La fecha de fin es obligatoria.';
      this.cdr.detectChanges();
      return;
    }

    if (!this.formulario.clienteId) {
      this.error = 'Debes seleccionar un cliente.';
      this.cdr.detectChanges();
      return;
    }

    this.formulario.entrenadorId = this.entrenadorId;
    this.guardando = true;
    this.cdr.detectChanges();

    if (this.modoEdicion && this.idPlanEditando) {
      this.planesService.actualizarPlan(this.idPlanEditando, this.formulario)
        .pipe(timeout(this.requestTimeoutMs))
        .subscribe({
          next: () => {
            this.guardando = false;
            this.resetFormulario();
            this.cargarPlanes();
            this.cdr.detectChanges();
          },
          error: (err: any) => {
            console.error('Error actualizando plan', err);
            this.error = this.obtenerMensajeError(err, 'No se pudo actualizar el plan.');
            this.guardando = false;
            this.cdr.detectChanges();
          }
        });
      return;
    }

    this.planesService.crearPlan(this.formulario)
      .pipe(timeout(this.requestTimeoutMs))
      .subscribe({
        next: () => {
          this.guardando = false;
          this.resetFormulario();
          this.cargarPlanes();
          this.cdr.detectChanges();
        },
        error: (err: any) => {
          console.error('Error creando plan', err);
          this.error = this.obtenerMensajeError(err, 'No se pudo crear el plan.');
          this.guardando = false;
          this.cdr.detectChanges();
        }
      });
  }

  editarPlan(plan: PlanEntrenamientoDTO): void {
    if (this.guardando || this.cambiandoEstadoId) {
      return;
    }

    this.error = null;
    this.modoEdicion = true;
    this.mostrarFormularioCrear = true;
    this.idPlanEditando = plan.id;

    this.formulario = {
      nombre: plan.nombre || '',
      descripcion: plan.descripcion || '',
      objetivo: plan.objetivo || '',
      fechaInicio: plan.fechaInicio || '',
      fechaFin: plan.fechaFin || '',
      clienteId: plan.clienteId || '',
      entrenadorId: plan.entrenadorId || '',
    };

    this.clienteSeleccionado = {
      id: plan.clienteId,
      nombres: plan.clienteNombre || 'Cliente',
      apellidos: '',
      numDoc: ''
    };

    this.terminoBusquedaCliente = plan.clienteNombre || plan.clienteId || '';
    this.clientes = [];

    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.cdr.detectChanges();
  }

  cambiarEstado(plan: PlanEntrenamientoDTO): void {
    if (this.cambiandoEstadoId || this.guardando) {
      return;
    }

    const nuevoEstado = !this.normalizarActivo(plan.activo);
    this.error = null;
    this.cambiandoEstadoId = plan.id;
    this.cdr.detectChanges();

    this.planesService.cambiarEstado(plan.id, nuevoEstado)
      .pipe(timeout(this.requestTimeoutMs))
      .subscribe({
        next: () => {
          this.cambiandoEstadoId = null;
          this.cargarPlanes();
          this.cdr.detectChanges();
        },
        error: (err: any) => {
          console.error('Error cambiando estado del plan', err);
          this.error = this.obtenerMensajeError(err, 'No se pudo cambiar el estado del plan.');
          this.cambiandoEstadoId = null;
          this.cdr.detectChanges();
        }
      });
  }

  cancelarFormulario(): void {
    if (this.guardando) {
      return;
    }

    this.resetFormulario();
    this.error = null;
    this.cdr.detectChanges();
  }

  resetFormulario(): void {
    this.modoEdicion = false;
    this.mostrarFormularioCrear = false;
    this.idPlanEditando = null;

    this.terminoBusquedaCliente = '';
    this.clientes = [];
    this.clienteSeleccionado = null;

    this.formulario = this.crearFormularioVacio();
  }

  volver(): void {
    this.router.navigate(['/entrenador']);
  }

  actualizar(): void {
    if (this.guardando || this.cargando || !!this.cambiandoEstadoId) {
      return;
    }

    this.cargarPlanes();
  }

  private crearFormularioVacio(): PlanEntrenamientoCrearDTO {
    return {
      nombre: '',
      descripcion: '',
      objetivo: '',
      fechaInicio: '',
      fechaFin: '',
      clienteId: '',
      entrenadorId: '',
    };
  }

  private obtenerMensajeError(err: any, mensajePorDefecto: string): string {
    if (err?.name === 'TimeoutError') {
      return 'El servidor tardó demasiado en responder. Intenta nuevamente.';
    }

    return (
      err?.error?.message ||
      err?.error?.mensaje ||
      err?.error?.error ||
      mensajePorDefecto
    );
  }

  private construirNombreCliente(cliente: any): string {
    const nombreCompleto = `${cliente?.nombres || ''} ${cliente?.apellidos || ''}`.trim();
    const documento = cliente?.numDoc ? ` - ${cliente.numDoc}` : '';
    return `${nombreCompleto}${documento}`.trim();
  }

  private normalizarPlanes(data: any): PlanEntrenamientoDTO[] {
    const lista = Array.isArray(data)
      ? data
      : Array.isArray(data?.data)
        ? data.data
        : Array.isArray(data?.content)
          ? data.content
          : [];

    return lista.map((plan: any) => ({
      ...plan,
      activo: this.normalizarActivo(plan?.activo),
    }));
  }

  private normalizarActivo(valor: any): boolean {
    if (typeof valor === 'boolean') {
      return valor;
    }

    if (typeof valor === 'string') {
      const normalizado = valor.trim().toLowerCase();
      return normalizado === 'true' || normalizado === 'activo' || normalizado === '1';
    }

    if (typeof valor === 'number') {
      return valor === 1;
    }

    return !!valor;
  }
}
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { PlanEntrenamientoDTO, Planes } from '../../../admin/planes';
import { RutinaDTO, RutinaRequest, RutinaService } from '../../../admin/rutina';

interface PlanRutinasResumen {
  plan: PlanEntrenamientoDTO;
  totalRutinas: number;
  rutinasActivas: number;
  estadoRutinas: 'PENDIENTE' | 'EN_PROCESO' | 'COMPLETO';
}

@Component({
  selector: 'app-mis-rutinas',
  standalone: false,
  templateUrl: './mis-rutinas.html',
  styleUrl: './mis-rutinas.scss',
})
export class MisRutinas implements OnInit {
  cargando = false;
  guardando = false;
  cambiandoEstadoId: string | null = null;

  error = '';
  mensajeExito = '';

  planIdSeleccionado: string | null = null;
  planSeleccionado: PlanEntrenamientoDTO | null = null;

  planesResumen: PlanRutinasResumen[] = [];
  planesResumenFiltrados: PlanRutinasResumen[] = [];

  rutinas: RutinaDTO[] = [];
  rutinasFiltradas: RutinaDTO[] = [];

  terminoBusqueda = '';
  filtroEstadoPlan = 'TODOS';
  diaSeleccionado = 'TODOS';

  mostrarFormulario = false;
  modoEdicion = false;
  rutinaEditandoId: string | null = null;

  formulario: RutinaRequest = {
    planId: '',
    nombre: '',
    descripcion: '',
    diaSemana: 'Lunes',
    orden: 1,
    activo: true,
  };

  filtrosPlan = [
    { valor: 'TODOS', etiqueta: 'Todos' },
    { valor: 'PENDIENTE', etiqueta: 'Pendientes' },
    { valor: 'EN_PROCESO', etiqueta: 'En proceso' },
    { valor: 'COMPLETO', etiqueta: 'Completos' },
  ];

  dias = [
    { valor: 'TODOS', etiqueta: 'Todos' },
    { valor: 'LUNES', etiqueta: 'Lunes' },
    { valor: 'MARTES', etiqueta: 'Martes' },
    { valor: 'MIERCOLES', etiqueta: 'Miércoles' },
    { valor: 'JUEVES', etiqueta: 'Jueves' },
    { valor: 'VIERNES', etiqueta: 'Viernes' },
    { valor: 'SABADO', etiqueta: 'Sábado' },
    { valor: 'DOMINGO', etiqueta: 'Domingo' },
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private planesService: Planes,
    private rutinaService: RutinaService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const planId = params.get('planId') || params.get('id');

      console.log('PLAN ID RECIBIDO EN MIS RUTINAS:', planId);

      this.planIdSeleccionado = planId;

      if (this.planIdSeleccionado) {
        this.cargarGestionPlan(this.planIdSeleccionado);
      } else {
        this.cargarTableroPlanes();
      }
    });
  }

  cargarTableroPlanes(): void {
    this.cargando = true;
    this.error = '';
    this.mensajeExito = '';
    this.planSeleccionado = null;
    this.rutinas = [];
    this.rutinasFiltradas = [];
    this.cancelarFormulario();

    this.planesService.getPlanes().subscribe({
      next: (planes) => {
        const listaPlanes = planes || [];

        if (listaPlanes.length === 0) {
          this.planesResumen = [];
          this.planesResumenFiltrados = [];
          this.cargando = false;
          this.cdr.detectChanges();
          return;
        }

        const consultas = listaPlanes.map(plan =>
          this.rutinaService.getRutinasPorPlanGestion(plan.id).pipe(
            catchError((err) => {
              console.error('Error cargando rutinas del plan', plan.id, err);
              return of([] as RutinaDTO[]);
            })
          )
        );

        forkJoin(consultas).subscribe({
          next: (rutinasPorPlan) => {
            this.planesResumen = listaPlanes.map((plan, index) => {
              const rutinasPlan = rutinasPorPlan[index] || [];
              const rutinasActivas = rutinasPlan.filter(r => r.activo).length;

              return {
                plan,
                totalRutinas: rutinasPlan.length,
                rutinasActivas,
                estadoRutinas: this.calcularEstadoRutinas(rutinasPlan),
              };
            });

            this.aplicarFiltrosPlanes();
            this.cargando = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            console.error('Error armando tablero de rutinas', err);
            this.error = 'No se pudo cargar el resumen de rutinas.';
            this.cargando = false;
            this.cdr.detectChanges();
          }
        });
      },
      error: (err) => {
        console.error('Error cargando planes', err);
        this.error = this.obtenerMensajeError(err, 'No se pudieron cargar tus planes.');
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  cargarGestionPlan(planId: string): void {
    this.cargando = true;
    this.error = '';
    this.mensajeExito = '';
    this.mostrarFormulario = false;
    this.modoEdicion = false;
    this.rutinaEditandoId = null;

    this.planesService.getPlanes().subscribe({
      next: (planes) => {
        const listaPlanes = planes || [];

        this.planSeleccionado =
          listaPlanes.find(plan => plan.id === planId) || null;

        if (!this.planSeleccionado) {
          this.error = 'No se encontró el plan seleccionado.';
          this.rutinas = [];
          this.rutinasFiltradas = [];
          this.cargando = false;
          this.cdr.detectChanges();
          return;
        }

        this.rutinaService.getRutinasPorPlanGestion(planId).subscribe({
          next: (rutinas) => {
            this.rutinas = (rutinas || []).sort(
              (a, b) => (a.orden || 0) - (b.orden || 0)
            );

            this.aplicarFiltroDia();

            this.cargando = false;
            this.cdr.detectChanges();
          },
          error: (err) => {
            console.error('Error cargando rutinas del plan', err);

            this.error = this.obtenerMensajeError(
              err,
              'No se pudieron cargar las rutinas del plan.'
            );

            this.rutinas = [];
            this.rutinasFiltradas = [];
            this.cargando = false;
            this.cdr.detectChanges();
          }
        });
      },
      error: (err) => {
        console.error('Error cargando planes para buscar plan seleccionado', err);

        this.error = this.obtenerMensajeError(
          err,
          'No se pudo cargar el plan seleccionado.'
        );

        this.planSeleccionado = null;
        this.rutinas = [];
        this.rutinasFiltradas = [];
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  } 

  calcularEstadoRutinas(rutinas: RutinaDTO[]): 'PENDIENTE' | 'EN_PROCESO' | 'COMPLETO' {
    const activas = rutinas.filter(r => r.activo).length;

    if (activas === 0) {
      return 'PENDIENTE';
    }

    if (activas >= 3) {
      return 'COMPLETO';
    }

    return 'EN_PROCESO';
  }

  aplicarFiltrosPlanes(): void {
    const termino = this.normalizarTexto(this.terminoBusqueda);

    this.planesResumenFiltrados = this.planesResumen.filter(item => {
      const plan = item.plan;

      const coincideTexto =
        !termino ||
        this.normalizarTexto(plan.nombre).includes(termino) ||
        this.normalizarTexto(plan.objetivo).includes(termino) ||
        this.normalizarTexto(plan.clienteNombre || plan.clienteId).includes(termino);

      const coincideEstado =
        this.filtroEstadoPlan === 'TODOS' ||
        item.estadoRutinas === this.filtroEstadoPlan;

      return coincideTexto && coincideEstado;
    });
  }

  seleccionarFiltroPlan(filtro: string): void {
    this.filtroEstadoPlan = filtro;
    this.aplicarFiltrosPlanes();
  }

  seleccionarDia(dia: string): void {
    this.diaSeleccionado = dia;
    this.aplicarFiltroDia();
  }

  aplicarFiltroDia(): void {
    if (this.diaSeleccionado === 'TODOS') {
      this.rutinasFiltradas = [...this.rutinas];
      return;
    }

    this.rutinasFiltradas = this.rutinas.filter(rutina =>
      this.normalizarDiaFiltro(rutina.diaSemana) === this.diaSeleccionado
    );
  }

  gestionarRutinas(plan: PlanEntrenamientoDTO): void {
    if (!plan?.id) {
      this.error = 'No se pudo identificar el plan seleccionado.';
      this.cdr.detectChanges();
      return;
    }

    this.router.navigate(['/entrenador/MisRutinas', plan.id]);
  }

  mostrarCrearRutina(): void {
    if (!this.planSeleccionado) {
      this.error = 'Primero selecciona un plan para crear rutinas.';
      this.cdr.detectChanges();
      return;
    }

    this.mostrarFormulario = true;
    this.modoEdicion = false;
    this.rutinaEditandoId = null;

    this.formulario = {
      planId: this.planSeleccionado.id,
      nombre: '',
      descripcion: '',
      diaSemana: 'Lunes',
      orden: this.rutinas.length + 1,
      activo: true,
    };
  }

  editarRutina(rutina: RutinaDTO): void {
    this.mostrarFormulario = true;
    this.modoEdicion = true;
    this.rutinaEditandoId = rutina.id;

    this.formulario = {
      planId: rutina.planId,
      nombre: rutina.nombre,
      descripcion: rutina.descripcion || '',
      diaSemana: rutina.diaSemana || 'Lunes',
      orden: rutina.orden || 1,
      activo: rutina.activo,
    };
  }

  guardarRutina(): void {
    this.error = '';
    this.mensajeExito = '';

    if (!this.validarFormulario()) {
      return;
    }

    this.guardando = true;

    if (this.modoEdicion && this.rutinaEditandoId) {
      this.rutinaService.actualizarRutina(this.rutinaEditandoId, this.formulario).subscribe({
        next: () => {
          this.mensajeExito = 'Rutina actualizada correctamente.';
          this.guardando = false;
          this.cancelarFormulario();
          this.recargarVistaActual();
        },
        error: (err) => {
          console.error('Error actualizando rutina', err);
          this.error = this.obtenerMensajeError(err, 'No se pudo actualizar la rutina.');
          this.guardando = false;
          this.cdr.detectChanges();
        }
      });

      return;
    }

    this.rutinaService.crearRutina(this.formulario).subscribe({
      next: () => {
        this.mensajeExito = 'Rutina creada correctamente.';
        this.guardando = false;
        this.cancelarFormulario();
        this.recargarVistaActual();
      },
      error: (err) => {
        console.error('Error creando rutina', err);
        this.error = this.obtenerMensajeError(err, 'No se pudo crear la rutina.');
        this.guardando = false;
        this.cdr.detectChanges();
      }
    });
  }

  cambiarEstadoRutina(rutina: RutinaDTO): void {
    this.error = '';
    this.mensajeExito = '';
    this.cambiandoEstadoId = rutina.id;

    const nuevoEstado = !rutina.activo;

    this.rutinaService.cambiarEstadoRutina(rutina.id, nuevoEstado).subscribe({
      next: () => {
        this.mensajeExito = nuevoEstado
          ? 'Rutina activada correctamente.'
          : 'Rutina inactivada correctamente.';

        this.cambiandoEstadoId = null;
        this.recargarVistaActual();
      },
      error: (err) => {
        console.error('Error cambiando estado de rutina', err);
        this.error = this.obtenerMensajeError(err, 'No se pudo cambiar el estado de la rutina.');
        this.cambiandoEstadoId = null;
        this.cdr.detectChanges();
      }
    });
  }

  irAEjercicios(rutina: RutinaDTO): void {
    if (!rutina?.id) {
      this.error = 'No se pudo identificar la rutina seleccionada.';
      this.cdr.detectChanges();
      return;
    }

    this.router.navigate(['/entrenador/RutinaEjercicios', rutina.id]);
  }
  
  cancelarFormulario(): void {
    this.mostrarFormulario = false;
    this.modoEdicion = false;
    this.rutinaEditandoId = null;

    this.formulario = {
      planId: this.planSeleccionado?.id || '',
      nombre: '',
      descripcion: '',
      diaSemana: 'Lunes',
      orden: 1,
      activo: true,
    };
  }

  validarFormulario(): boolean {
    if (!this.formulario.planId) {
      this.error = 'El plan es obligatorio.';
      return false;
    }

    if (!this.formulario.nombre || !this.formulario.nombre.trim()) {
      this.error = 'El nombre de la rutina es obligatorio.';
      return false;
    }

    if (!this.formulario.diaSemana || !this.formulario.diaSemana.trim()) {
      this.error = 'El día de la semana es obligatorio.';
      return false;
    }

    if (!this.formulario.orden || this.formulario.orden < 1) {
      this.error = 'El orden debe ser mayor o igual a 1.';
      return false;
    }

    return true;
  }

  recargarVistaActual(): void {
    if (this.planIdSeleccionado) {
      this.cargarGestionPlan(this.planIdSeleccionado);
    } else {
      this.cargarTableroPlanes();
    }
  }

  actualizar(): void {
    this.recargarVistaActual();
  }

  volver(): void {
    if (this.planIdSeleccionado) {
      this.router.navigate(['/entrenador/MisRutinas']);
      return;
    }

    this.router.navigate(['/entrenador']);
  }

  obtenerClaseEstadoPlan(estado: string): string {
    return estado.toLowerCase();
  }

  obtenerTextoEstadoPlan(estado: string): string {
    switch (estado) {
      case 'PENDIENTE':
        return 'Pendiente';
      case 'EN_PROCESO':
        return 'En proceso';
      case 'COMPLETO':
        return 'Completo';
      default:
        return estado;
    }
  }

  normalizarDiaFiltro(dia: string): string {
    return this.normalizarTexto(dia)
      .replace('MIERCOLES', 'MIERCOLES')
      .replace('SABADO', 'SABADO');
  }

  normalizarTexto(texto: string | undefined | null): string {
    return (texto || '')
      .toString()
      .trim()
      .toUpperCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '');
  }

  obtenerMensajeError(err: any, mensajeDefault: string): string {
    return err?.error?.message ||
      err?.error?.mensaje ||
      err?.message ||
      mensajeDefault;
  }
}

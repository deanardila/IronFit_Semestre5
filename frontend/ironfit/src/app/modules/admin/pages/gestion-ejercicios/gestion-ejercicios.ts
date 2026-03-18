import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Ejercicios, EjercicioDTO, EjercicioCrearDTO } from '../../ejercicios';
import { Router } from '@angular/router';
import { finalize, timeout } from 'rxjs';

@Component({
  selector: 'app-gestion-ejercicios',
  standalone: false,
  templateUrl: './gestion-ejercicios.html',
  styleUrls: ['./gestion-ejercicios.scss'],
})
export class GestionEjercicios implements OnInit {
  ejercicios: EjercicioDTO[] = [];
  ejerciciosFiltrados: EjercicioDTO[] = [];
  cargando = false;
  error: string | null = null;
  private timeoutCargaId: ReturnType<typeof setTimeout> | null = null;
  terminoBusqueda = '';
  mostrarFormularioCrear = false;

  modoEdicion = false;
  idEjercicioEditando: string | null = null;

  formulario: EjercicioCrearDTO = {
    nombre: '',
    descripcion: '',
    categoria: '',
    grupoMuscular: '',
    seriesSugeridas: null,
    repeticionesSugeridas: null,
    tipoEquipo: '',
  };

  constructor(
    private ejerciciosService: Ejercicios,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarEjercicios();
  }

  cargarEjercicios(): void {
    this.cancelarTimeoutCarga();
    this.cargando = true;
    this.error = null;
    this.cdr.detectChanges();

    // Respaldo visual para evitar estado de carga infinito.
    this.timeoutCargaId = setTimeout(() => {
      if (!this.cargando) {
        return;
      }

      this.cargando = false;
      this.error = 'La carga tardó demasiado. Intenta actualizar nuevamente.';
      this.cdr.detectChanges();
    }, 12000);

    this.ejerciciosService.getEjercicios().pipe(
      timeout(10000),
      finalize(() => {
        this.cargando = false;
        this.cancelarTimeoutCarga();
        this.cdr.detectChanges();
      })
    ).subscribe({
      next: (data) => {
        this.ejercicios = this.normalizarListaEjercicios(data);
        this.aplicarFiltros();
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando ejercicios', err);
        const status = err?.status;
        const backendMessage = err?.error?.message || err?.message;

        if (status === 0) {
          this.error = 'No hay conexión con el servidor de ejercicios.';
        } else if (status) {
          this.error = `No se pudieron cargar los ejercicios (HTTP ${status}). ${backendMessage || ''}`.trim();
        } else {
          this.error = backendMessage || 'No se pudieron cargar los ejercicios.';
        }

        this.ejercicios = [];
        this.ejerciciosFiltrados = [];
        this.cdr.detectChanges();
      },
    });
  }

  aplicarFiltros(): void {
    const busqueda = this.terminoBusqueda.trim().toLowerCase();

    if (!busqueda) {
      this.ejerciciosFiltrados = [...this.ejercicios];
      return;
    }

    this.ejerciciosFiltrados = this.ejercicios.filter((e) => {
      const nombre = (e.nombre || '').toLowerCase();
      const categoria = (e.categoria || '').toLowerCase();
      const grupoMuscular = (e.grupoMuscular || '').toLowerCase();
      const descripcion = (e.descripcion || '').toLowerCase();

      return nombre.includes(busqueda)
        || categoria.includes(busqueda)
        || grupoMuscular.includes(busqueda)
        || descripcion.includes(busqueda);
    });
  }

  private normalizarListaEjercicios(data: unknown): EjercicioDTO[] {
    if (Array.isArray(data)) {
      return data;
    }

    if (!data || typeof data !== 'object') {
      return [];
    }

    const wrapper = data as {
      data?: unknown;
      content?: unknown;
      ejercicios?: unknown;
      results?: unknown;
      items?: unknown;
    };

    const posiblesListas = [
      wrapper.data,
      wrapper.content,
      wrapper.ejercicios,
      wrapper.results,
      wrapper.items,
    ];

    for (const lista of posiblesListas) {
      if (Array.isArray(lista)) {
        return lista as EjercicioDTO[];
      }
    }

    return [];
  }

  private cancelarTimeoutCarga(): void {
    if (!this.timeoutCargaId) {
      return;
    }

    clearTimeout(this.timeoutCargaId);
    this.timeoutCargaId = null;
  }

  guardarEjercicio(): void {
    this.error = null;

    if (this.modoEdicion && this.idEjercicioEditando) {
      this.ejerciciosService.actualizarEjercicio(this.idEjercicioEditando, this.formulario).subscribe({
        next: () => {
          this.resetFormulario();
          this.cargarEjercicios();
          this.mostrarFormularioCrear = false;
        },
        error: (err) => {
          console.error('Error actualizando ejercicio', err);
          this.error = err?.error?.message || 'No se pudo actualizar el ejercicio.';
        }
      });
      return;
    }

    this.ejerciciosService.crearEjercicio(this.formulario).subscribe({
      next: () => {
        this.resetFormulario();
        this.cargarEjercicios();
        this.mostrarFormularioCrear = false;
      },
      error: (err) => {
        console.error('Error creando ejercicio', err);
        this.error = err?.error?.message || 'No se pudo crear el ejercicio.';
      }
    });
  }

  editarEjercicio(ejercicio: EjercicioDTO): void {
    this.mostrarFormularioCrear = true;
    this.modoEdicion = true;
    this.idEjercicioEditando = ejercicio.id;

    this.formulario = {
      nombre: ejercicio.nombre,
      descripcion: ejercicio.descripcion,
      categoria: ejercicio.categoria,
      grupoMuscular: ejercicio.grupoMuscular,
      seriesSugeridas: ejercicio.seriesSugeridas,
      repeticionesSugeridas: ejercicio.repeticionesSugeridas,
      tipoEquipo: ejercicio.tipoEquipo || '',
    };
  }

  eliminarEjercicio(id: string): void {
    const confirmar = window.confirm('¿Seguro que deseas eliminar este ejercicio?');
    if (!confirmar) return;

    this.ejerciciosService.eliminarEjercicio(id).subscribe({
      next: () => {
        if (this.idEjercicioEditando === id) {
          this.resetFormulario();
        }
        this.cargarEjercicios();
      },
      error: (err) => {
        console.error('Error eliminando ejercicio', err);
        this.error = err?.error?.message || 'No se pudo eliminar el ejercicio.';
      }
    });
  }

  resetFormulario(): void {
    this.modoEdicion = false;
    this.idEjercicioEditando = null;

    this.formulario = {
      nombre: '',
      descripcion: '',
      categoria: '',
      grupoMuscular: '',
      seriesSugeridas: null,
      repeticionesSugeridas: null,
      tipoEquipo: '',
    };
  }

  abrirFormularioCrear(): void {
    this.toggleFormularioCrear(true);
  }

  toggleFormularioCrear(forzarApertura = false): void {
    const debeAbrir = forzarApertura || !(this.mostrarFormularioCrear || this.modoEdicion);

    if (!debeAbrir) {
      this.cancelarCrear();
      return;
    }

    this.mostrarFormularioCrear = true;
    this.modoEdicion = false;
    this.idEjercicioEditando = null;
    this.resetFormulario();
    this.cdr.detectChanges();
  }

  cancelarCrear(): void {
    this.mostrarFormularioCrear = false;
    this.resetFormulario();
    this.cdr.detectChanges();
  }

  actualizar(): void {
    this.cargarEjercicios();
  }

  volver(): void {
    this.router.navigate(['/admin']);
  }
}
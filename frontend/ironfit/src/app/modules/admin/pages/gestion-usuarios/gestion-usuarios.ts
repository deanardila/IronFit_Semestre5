import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuarios, UsuarioResumen } from '../../usuarios';
import { finalize, timeout } from 'rxjs';

@Component({
  selector: 'app-gestion-usuarios',
  standalone: false,
  templateUrl: './gestion-usuarios.html',
  styleUrls: ['./gestion-usuarios.scss'],
})
export class GestionUsuarios implements OnInit {

  usuarios: UsuarioResumen[] = [];
  usuariosFiltrados: UsuarioResumen[] = [];

  filtroTipo: string | null = null;    // 'cliente' | 'entrenador' | null
  filtroEstado: string | null = null;  // 'ACTIVO' | 'INACTIVO' | null
  
  // Búsqueda por cédula o nombre
  terminoBusqueda: string = '';

  cargando = false;
  vieneDeDashboard = false;

  // Control del formulario
  mostrarFormularioCrear = false;

  // Modelo del formulario de creación
  nuevoUsuario: any = {
  tipoDoc: 'CC',      // valor por defecto
  numDoc: '',
  nombres: '',
  apellidos: '',
  correo: '',
  telefono: '',
  activo: true
};

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private usuariosApi: Usuarios,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      console.log('Query params recibidos:', params);

      if (params['tipo'] || params['estado']) {
        // Viene de las tarjetas del dashboard
        this.vieneDeDashboard = true;
        this.filtroTipo = params['tipo'] ?? null;
        this.filtroEstado = params['estado'] ?? null;
      } else {
        // Viene del menú hamburguesa
        this.vieneDeDashboard = false;
        this.filtroTipo = null;
        this.filtroEstado = null;
      }

      console.log('Filtros iniciales -> tipo:', this.filtroTipo, 'estado:', this.filtroEstado);
      this.cargarUsuarios();
    });
  }

  cargarUsuarios(): void {
    this.cargando = true;
    this.cdr.detectChanges();

    console.log('Cargando usuarios...');

    this.usuariosApi.getUsuariosResumen().pipe(
      timeout(10000),
      finalize(() => {
        this.cargando = false;
        this.cdr.detectChanges();
      })
    ).subscribe({
      next: (lista) => {
        console.log('Usuarios resumen desde API:', lista);
        this.usuarios = lista ?? [];

        if (this.usuarios.length > 0) {
          console.log('Ejemplo usuario[0]:', this.usuarios[0]);
        }

        this.aplicarFiltros();
        console.log('Carga terminada, cargando =', this.cargando);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando usuarios', err);
        this.usuarios = [];
        this.usuariosFiltrados = [];
        if (err?.name === 'TimeoutError') {
          console.warn('Timeout cargando usuarios.');
        }
        this.cdr.detectChanges();
      }
    });
  }

  aplicarFiltros(): void {
    console.log('Aplicando filtros -> tipo:', this.filtroTipo, 'estado:', this.filtroEstado, 'búsqueda:', this.terminoBusqueda);

    const tipoActual = this.filtroTipo;
    const estadoActual = this.filtroEstado;
    const busqueda = this.terminoBusqueda.toLowerCase().trim();

    this.usuariosFiltrados = this.usuarios.filter(u => {
      let coincideTipo = true;
      let coincideEstado = true;
      let coincideBusqueda = true;

      // TIPO
      if (tipoActual) {
        const tipoBuscado = tipoActual.toUpperCase();

        if (Array.isArray(u.roles) && u.roles.length > 0) {
          coincideTipo = u.roles
            .map(r => ('' + r).toUpperCase())
            .some(r => r.includes(tipoBuscado));
        } else {
          coincideTipo = false;
        }
      }

      // ESTADO
      if (estadoActual) {
        const activoBuscado = estadoActual === 'ACTIVO';
        const activo = !!u.activo;
        coincideEstado = activo === activoBuscado;
      }

      // BÚSQUEDA por cédula o nombre
      if (busqueda) {
        const numDoc = (u as any).numDoc ? String((u as any).numDoc).toLowerCase() : '';
        const nombres = (u.nombres || '').toLowerCase();
        const apellidos = (u.apellidos || '').toLowerCase();
        const nombreCompleto = `${nombres} ${apellidos}`.trim();
        
        coincideBusqueda = numDoc.includes(busqueda) || 
                          nombres.includes(busqueda) || 
                          apellidos.includes(busqueda) ||
                          nombreCompleto.includes(busqueda);
      }

      return coincideTipo && coincideEstado && coincideBusqueda;
    });

    console.log('Usuarios filtrados:', this.usuariosFiltrados);

    if (estadoActual && this.vieneDeDashboard && this.usuariosFiltrados.length === 0) {
      console.warn('Sin resultados con estado. Mostrando solo por tipo.');
      this.filtroEstado = null;

      this.usuariosFiltrados = this.usuarios.filter(u => {
        if (!tipoActual) return true;
        const tipoBuscado = tipoActual.toUpperCase();
        return Array.isArray(u.roles) &&
                u.roles.map(r => ('' + r).toUpperCase()).some(r => r.includes(tipoBuscado));
      });

      console.log('Usuarios filtrados (solo tipo):', this.usuariosFiltrados);
    }
  }

  // Abrir / cerrar formulario
  abrirFormularioCrear(): void {
  this.toggleFormularioCrear(true);
}

  toggleFormularioCrear(forzarApertura = false): void {
  const debeAbrir = forzarApertura || !this.mostrarFormularioCrear;

  if (!debeAbrir) {
    this.cancelarCrear();
    return;
  }

  this.mostrarFormularioCrear = true;
  this.nuevoUsuario = {
    tipoDoc: 'CC',
    numDoc: '',
    nombres: '',
    apellidos: '',
    correo: '',
    telefono: '',
    activo: true,
    rol: 'CLIENTE'
  };
  this.cdr.detectChanges();
}


  cancelarCrear(): void {
    this.mostrarFormularioCrear = false;
    this.cdr.detectChanges();
  }

  actualizar(): void {
    this.cargarUsuarios();
  }

  //Guardar el usuario
guardarUsuario(): void {
  const payload = {
    tipoDoc: this.nuevoUsuario.tipoDoc,
    numDoc: this.nuevoUsuario.numDoc,
    nombres: this.nuevoUsuario.nombres,
    apellidos: this.nuevoUsuario.apellidos,
    correo: this.nuevoUsuario.correo,
    telefono: this.nuevoUsuario.telefono,
    password: 'Ironfit123*', // Contraseña por defecto
    roles: [this.nuevoUsuario.rol]
  };

  console.log('Guardando nuevo usuario (payload):', payload);

  this.usuariosApi.crearUsuario(payload).subscribe({
    next: (usuarioCreado) => {
      console.log('Usuario creado en backend:', usuarioCreado);
      this.mostrarFormularioCrear = false;
      this.cargarUsuarios(); // recarga desde /resumen
    },
    error: (err) => {
      console.error('Error creando usuario', err);
    }
  });

}

toggleEstado(usuario: UsuarioResumen): void {
  const nuevoEstado = !usuario.activo;

  this.usuariosApi.cambiarEstado(usuario.id, nuevoEstado).subscribe({
    next: (usuarioActualizado) => {
      console.log('Estado actualizado:', usuarioActualizado);

      usuario.activo = usuarioActualizado.activo;
      this.aplicarFiltros();
      this.cdr.detectChanges();
    },
    error: (err) => {
      console.error('Error cambiando estado del usuario', err);
    }
  });
}

resetearPassword(usuario: UsuarioResumen): void {
  const confirmar = confirm(`¿Resetear la contraseña de ${usuario.nombres} ${usuario.apellidos} a la clave general?`);

  if (!confirmar) return;

  this.usuariosApi.resetearPassword(usuario.id).subscribe({
    next: () => {
      console.log('Contraseña reseteada correctamente');
      alert('Contraseña restablecida a: Ironfit123*');
    },
    error: (err) => {
      console.error('Error reseteando contraseña', err);
    }
  });
}

volverAlDashboard(): void {
  this.router.navigate(['/admin']);
}

}

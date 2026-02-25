import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Usuarios, UsuarioResumen } from '../../usuarios';

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

  // 👉 Nuevo: control del formulario
  mostrarFormularioCrear = false;

  // 👉 Nuevo: modelo del formulario de creación
  nuevoUsuario: any = {
  tipoDoc: 'CC',      // valor por defecto
  nroDoc: '',
  nombres: '',
  apellidos: '',
  email: '',
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

    this.usuariosApi.getUsuariosResumen().subscribe({
      next: (lista) => {
        console.log('Usuarios resumen desde API:', lista);
        this.usuarios = lista ?? [];

        if (this.usuarios.length > 0) {
          console.log('Ejemplo usuario[0]:', this.usuarios[0]);
        }

        this.aplicarFiltros();

        this.cargando = false;
        console.log('Carga terminada, cargando =', this.cargando);
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando usuarios', err);
        this.usuarios = [];
        this.usuariosFiltrados = [];
        this.cargando = false;
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
        const nroDoc = (u as any).nroDoc ? String((u as any).nroDoc).toLowerCase() : '';
        const nombres = (u.nombres || '').toLowerCase();
        const apellidos = (u.apellidos || '').toLowerCase();
        const nombreCompleto = `${nombres} ${apellidos}`.trim();
        
        coincideBusqueda = nroDoc.includes(busqueda) || 
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

  // 👉 Nuevo: abrir / cerrar formulario
  abrirFormularioCrear(): void {
  this.mostrarFormularioCrear = true;
  this.nuevoUsuario = {
    tipoDoc: 'CC',
    nroDoc: '',
    nombres: '',
    apellidos: '',
    email: '',
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

  // 👉 Nuevo: guardar el usuario
  guardarUsuario(): void {
  const payload = {
    tipoDoc: this.nuevoUsuario.tipoDoc,
    nroDoc: this.nuevoUsuario.nroDoc,
    nombres: this.nuevoUsuario.nombres,
    apellidos: this.nuevoUsuario.apellidos,
    email: this.nuevoUsuario.email,
    telefono: this.nuevoUsuario.telefono,
    activo: this.nuevoUsuario.activo,

    usuarioLogin: (this.nuevoUsuario.email && this.nuevoUsuario.email.trim() !== '')
      ? this.nuevoUsuario.email.trim()
      : this.nuevoUsuario.nroDoc,

    clave: this.nuevoUsuario.nroDoc,
    rolNombre: this.nuevoUsuario.rol    // "ENTRENADOR", "CLIENTE", etc
  };

  console.log('Guardando nuevo usuario (payload):', payload);

  this.usuariosApi.crearUsuario(payload).subscribe({
    next: (usuarioCreado) => {
      console.log('Usuario creado en backend:', usuarioCreado);
      this.mostrarFormularioCrear = false;
      this.cargarUsuarios();  // recarga desde /resumen
    },
    error: (err) => {
      console.error('Error creando usuario', err);
    }
  });
}

volverAlDashboard(): void {
  this.router.navigate(['/admin']);
}

}

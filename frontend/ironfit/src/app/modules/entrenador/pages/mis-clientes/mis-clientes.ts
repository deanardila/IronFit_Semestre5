import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Usuarios, UsuarioResumen } from '../../../admin/usuarios';

@Component({
  selector: 'app-mis-clientes',
  standalone: false,
  templateUrl: './mis-clientes.html',
  styleUrls: ['./mis-clientes.scss'],
})
export class MisClientes implements OnInit {

  clientes: UsuarioResumen[] = [];
  clientesFiltrados: UsuarioResumen[] = [];
  cargando = false;
  nombreUsuario: string = '';
  terminoBusqueda: string = '';

  constructor(
    private router: Router,
    private usuariosApi: Usuarios,
    private cdr: ChangeDetectorRef,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    // Leer nombre guardado en localStorage
    this.nombreUsuario = localStorage.getItem('nombreUsuario') ?? 'Entrenador';
    this.route.queryParams.subscribe(params => {
      const buscar = params['buscar'];
      if (buscar) { this.terminoBusqueda = buscar; }
      this.cargarClientes();
    });
  }

  cargarClientes(): void {
    this.cargando = true;
    this.cdr.detectChanges();

    // Obtener todos los clientes activos
    this.usuariosApi.getUsuariosResumen().subscribe({
      next: (lista) => {
        // Filtrar solo clientes con rol CLIENTE y activos
        this.clientes = lista?.filter(u => {
          const tieneRolCliente = Array.isArray(u.roles) && 
            u.roles.map(r => ('' + r).toUpperCase()).some(r => r.includes('CLIENTE'));
          return tieneRolCliente && u.activo;
        }) ?? [];

        this.aplicarFiltros();
        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando clientes', err);
        this.clientes = [];
        this.clientesFiltrados = [];
        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  aplicarFiltros(): void {
    const b = this.terminoBusqueda.trim().toLowerCase();
    if (!b) {
      this.clientesFiltrados = [...this.clientes];
      return;
    }
    this.clientesFiltrados = this.clientes.filter(c => {
      const doc = (c.nroDoc || '').toLowerCase();
      const nom = (c.nombres || '').toLowerCase();
      const ape = (c.apellidos || '').toLowerCase();
      return doc.includes(b) || nom.includes(b) || ape.includes(b);
    });
  }

  volverAlDashboard(): void {
    this.router.navigate(['/entrenador']);
  }
}

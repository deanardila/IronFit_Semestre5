import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {
  AsignacionEntrenadorClienteDTO,
  AsignacionesService
} from '../../../admin/asignaciones';

interface ClienteAsignadoVista {
  id: string;
  nombres: string;
  apellidos: string;
  correo: string;
  telefono: string;
  numDoc: string;
  activo: boolean;
  asignacionId: string;
  entrenadorNombre: string;
}

@Component({
  selector: 'app-mis-clientes',
  standalone: false,
  templateUrl: './mis-clientes.html',
  styleUrls: ['./mis-clientes.scss'],
})
export class MisClientes implements OnInit {

  clientes: ClienteAsignadoVista[] = [];
  clientesFiltrados: ClienteAsignadoVista[] = [];

  cargando = false;
  error = '';

  nombreUsuario = '';
  terminoBusqueda = '';

  constructor(
    private router: Router,
    private asignacionesService: AsignacionesService,
    private cdr: ChangeDetectorRef,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.nombreUsuario = localStorage.getItem('nombreUsuario') ?? 'Entrenador';

    this.route.queryParams.subscribe(params => {
      const buscar = params['buscar'];

      if (buscar) {
        this.terminoBusqueda = buscar;
      }

      this.cargarClientes();
    });
  }

  cargarClientes(): void {
    this.cargando = true;
    this.error = '';
    this.cdr.detectChanges();

    this.asignacionesService.listarMisClientes().subscribe({
      next: (asignaciones) => {
        this.clientes = (asignaciones || [])
          .filter(asignacion => asignacion.activo)
          .map(asignacion => this.convertirAsignacionACliente(asignacion));

        this.aplicarFiltros();

        this.cargando = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando clientes asignados', err);

        this.error = 'No se pudieron cargar tus clientes asignados.';
        this.clientes = [];
        this.clientesFiltrados = [];

        this.cargando = false;
        this.cdr.detectChanges();
      }
    });
  }

  convertirAsignacionACliente(asignacion: AsignacionEntrenadorClienteDTO): ClienteAsignadoVista {
    const nombreCompleto = asignacion.clienteNombre || 'Cliente asignado';
    const partes = nombreCompleto.trim().split(' ');

    return {
      id: asignacion.clienteId,
      nombres: partes.slice(0, 2).join(' ') || nombreCompleto,
      apellidos: partes.length > 2 ? partes.slice(2).join(' ') : '',
      correo: asignacion.clienteCorreo || '-',
      telefono: '-',
      numDoc: asignacion.clienteDocumento || '',
      activo: asignacion.activo,
      asignacionId: asignacion.id,
      entrenadorNombre: asignacion.entrenadorNombre || '',
    };
  }

  aplicarFiltros(): void {
    const b = this.normalizarTexto(this.terminoBusqueda);

    if (!b) {
      this.clientesFiltrados = [...this.clientes];
      return;
    }

    this.clientesFiltrados = this.clientes.filter(c => {
      return (
        this.normalizarTexto(c.nombres).includes(b) ||
        this.normalizarTexto(c.apellidos).includes(b) ||
        this.normalizarTexto(c.correo).includes(b) ||
        this.normalizarTexto(c.numDoc).includes(b)
      );
    });
  }

  volverAlDashboard(): void {
    this.router.navigate(['/entrenador']);
  }

  normalizarTexto(texto: string | undefined | null): string {
    return (texto || '')
      .toString()
      .trim()
      .toLowerCase()
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '');
  }
}

import { Component, OnInit } from '@angular/core';
import { Ejercicios, EjercicioDTO } from '../../ejercicios';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gestion-ejercicios',
  standalone: false,
  templateUrl: './gestion-ejercicios.html',
  styleUrls: ['./gestion-ejercicios.scss'],
})
export class GestionEjercicios implements OnInit {

  ejercicios: EjercicioDTO[] = [];
  cargando = false;
  error: string | null = null;

  constructor(
    private ejerciciosService: Ejercicios,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarEjercicios();
  }

  cargarEjercicios(): void {
    this.cargando = true;
    this.error = null;

    this.ejerciciosService.getEjercicios().subscribe({
      next: (data) => {
        this.ejercicios = data || [];
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error cargando ejercicios', err);
        this.error = 'No se pudieron cargar los ejercicios.';
        this.cargando = false;
      },
    });
  }

  actualizar(): void {
    this.cargarEjercicios();
  }

  volver(): void {
    // Ajústalo a la ruta que necesites
    this.router.navigate(['/admin']);
    // o simplemente: window.history.back();
  }
}


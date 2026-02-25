import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { DashboardEntrenador } from './pages/dashboard-entrenador/dashboard-entrenador';
import { MisClientes } from './pages/mis-clientes/mis-clientes';
import { MisPlanes } from './pages/mis-planes/mis-planes';
import { MisRutinas } from './pages/mis-rutinas/mis-rutinas';
import { Asistencias } from './pages/asistencias/asistencias';
import { Evaluaciones } from './pages/evaluaciones/evaluaciones';

const routes: Routes = [
  { path: '', component: DashboardEntrenador },
  { path: 'MisClientes', component: MisClientes},
  { path: 'MisPlanes', component: MisPlanes },
  { path: 'MisRutinas', component: MisRutinas },
  { path: 'asistencias', component: Asistencias},
  { path: 'evaluaciones', component: Evaluaciones},
];

@NgModule({
  declarations: [
    DashboardEntrenador,
    MisClientes,
    MisPlanes,
    MisRutinas,
    Asistencias,
    Evaluaciones
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forChild(routes)
  ]
})
export class EntrenadorModule { }

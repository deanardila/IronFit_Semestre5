import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { DashboardCliente } from './pages/dashboard-cliente/dashboard-cliente';
import { MiPerfil } from './pages/mi-perfil/mi-perfil';
import { MiPlan } from './pages/mi-plan/mi-plan';
import { MisRutinas } from './pages/mis-rutinas/mis-rutinas';
import { MisAsistencias } from './pages/mis-asistencias/mis-asistencias';
import { MisEvaluaciones } from './pages/mis-evaluaciones/mis-evaluaciones';

const routes: Routes = [
  { path: '', component: DashboardCliente },
  { path: 'perfil', component: MiPerfil },
  { path: 'plan', component: MiPlan },
  { path: 'MisRutinas', component: MisRutinas },
  { path: 'asistencias', component: MisAsistencias },
  { path: 'evaluaciones', component: MisEvaluaciones},
];


@NgModule({
  declarations: [
    DashboardCliente,
    MiPerfil,
    MiPlan,
    MisRutinas,
    MisAsistencias,
    MisEvaluaciones
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forChild(routes),
  ]
})
export class ClienteModule { }

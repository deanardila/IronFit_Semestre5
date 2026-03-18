import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

import { DashboardAdmin } from './pages/dashboard-admin/dashboard-admin';
import { GestionUsuarios } from './pages/gestion-usuarios/gestion-usuarios';
import { GestionEjercicios } from './pages/gestion-ejercicios/gestion-ejercicios';
import { PlanesRutinas } from './pages/planes-rutinas/planes-rutinas';
import { AuditoriaReportes } from './pages/auditoria-reportes/auditoria-reportes';
import { FormsModule } from '@angular/forms';
import { MiPerfil } from '../cliente/pages/mi-perfil/mi-perfil';
import { SharedModule } from '../../shared/shared-module';


const routes: Routes = [
  { path: '', component: DashboardAdmin },
  { path: 'perfil', component: MiPerfil },
  { path: 'usuarios', component: GestionUsuarios },
  { path: 'ejercicios', component: GestionEjercicios },
  { path: 'planes-rutinas', component: PlanesRutinas },
  { path: 'auditoria', component: AuditoriaReportes },
];


@NgModule({
  declarations: [
    DashboardAdmin,
    GestionUsuarios,
    GestionEjercicios,
    PlanesRutinas,
    AuditoriaReportes
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    RouterModule.forChild(routes),
  ]
})
export class AdminModule { }

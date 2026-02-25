import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Auth } from './auth';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private auth: Auth, private router: Router) {}

  canActivate(route: any): boolean {
    const expectedRole = route.data['role'];
    const currentRole = this.auth.getRole();

    if (expectedRole !== currentRole) {
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }
}
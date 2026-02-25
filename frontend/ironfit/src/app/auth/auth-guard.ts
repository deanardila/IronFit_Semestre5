import { CanActivate, Router } from '@angular/router';
import { Auth } from './auth';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private auth: Auth, private router: Router) {}

  canActivate(): boolean {
    if (!this.auth.isLogged()) {
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }
}

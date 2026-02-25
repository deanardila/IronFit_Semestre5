import { Component, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from './auth/auth';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  standalone: false,
  styleUrl: './app.scss'
})
export class App {
  title = 'ironfit-frontend';

  constructor(
    public auth: Auth,    
    private router: Router
  ) {}

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}

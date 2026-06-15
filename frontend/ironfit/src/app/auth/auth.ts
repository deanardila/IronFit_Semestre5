import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class Auth {
  
  private apiUrl = 'https://ironfit-backend-production.up.railway.app/api/auth/login';

  constructor(private http: HttpClient) {}

  login(credenciales: { correo: string; password: string }): Observable<any> {
    return this.http.post<any>(this.apiUrl, credenciales);  
  }

  setSession(token: string, rol: string): void {
    localStorage.setItem('token', token);
    localStorage.setItem('rol', rol);
  }

  isLogged(): boolean {
    return !!localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('rol');
  }

  hasRole(role: string): boolean {
    return this.getRole() === role;
  }

  logout(): void {
    localStorage.clear();
  }
}


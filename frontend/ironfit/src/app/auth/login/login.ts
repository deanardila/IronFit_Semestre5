import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Auth } from '../auth';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  usuario: string = '';
  clave: string = '';
  mensajeError: string = '';

  constructor(
    private auth: Auth,
    private router: Router
  ) {}

  login() {

    // Validación básica
    if (!this.usuario || !this.clave) {
      this.mensajeError = 'Debe ingresar usuario y contraseña';
      return;
    }

    const credenciales = {
      usuario: this.usuario,
      clave: this.clave
    };
this.auth.login(credenciales).subscribe({
  next: (res: any) => {
    console.log('JSON recibido:', res); // debería mostrar { token, rol, usuario }

    this.auth.setSession(res.token, res.rol);

    const nombreDetectado =
        res.usuario?.nombres ??   
        res.nombres ??            
        res.nombre ??             
        res.username ??           
        this.usuario;             

      console.log('Nombre detectado para mostrar:', nombreDetectado);
      localStorage.setItem('nombreUsuario', nombreDetectado);

    switch (res.rol) {
      case 'ADMIN':
        this.router.navigate(['/admin']);
        break;
      case 'ENTRENADOR':
        this.router.navigate(['/entrenador']);
        break;
      case 'CLIENTE':
        this.router.navigate(['/cliente']);
        break;
      default:
        this.mensajeError = 'Rol desconocido';
        break;
    }
  },
  error: err => {
    console.error(err);
    this.mensajeError = 'Credenciales incorrectas';
  }
});
  }
}

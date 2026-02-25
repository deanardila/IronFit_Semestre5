package com.ironfit.ironfit.controlador.seguridad;

import com.ironfit.ironfit.modelo.seguridad.LoginRequest;
import com.ironfit.ironfit.modelo.seguridad.LoginResponse;
import com.ironfit.ironfit.modelo.seguridad.Usuario;
import com.ironfit.ironfit.modelo.seguridad.UsuarioRol;
import com.ironfit.ironfit.servicio.seguridad.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    String usuario = request.getUsuario();
    String clave   = request.getClave();

    Optional<Usuario> encontrado = authService.autenticar(usuario, clave);

    if (encontrado.isPresent()) {

        Usuario u = encontrado.get();

        // Tomamos el primer rol asociado al usuario
        UsuarioRol usuarioRol = u.getUsuarioRoles().iterator().next();
        String rol = usuarioRol.getRol().getNombreRol();   // ADMIN / ENTRENADOR / CLIENTE

        String token = "TEMP-" + u.getUsuarioLogin() + "-123";

        LoginResponse response = new LoginResponse(
            token,
            rol,
            u.getUsuarioLogin()
        );
    
        return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
}


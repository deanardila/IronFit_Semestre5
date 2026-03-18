package com.ironfit.backendmongo.controlador.seguridad;

import com.ironfit.backendmongo.dto.seguridad.LoginRequest;
import com.ironfit.backendmongo.dto.seguridad.LoginResponse;
import com.ironfit.backendmongo.servicio.seguridad.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService servicioAuth;

    public AuthController(AuthService servicioAuth) {
        this.servicioAuth = servicioAuth;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest solicitud) {
        return ResponseEntity.ok(servicioAuth.login(solicitud));
    }

    
}
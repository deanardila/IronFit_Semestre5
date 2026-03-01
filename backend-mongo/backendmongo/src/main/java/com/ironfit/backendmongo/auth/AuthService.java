package com.ironfit.backendmongo.auth;

import com.ironfit.backendmongo.security.jwt.JwtService;
import com.ironfit.backendmongo.usuario.UsuarioDoc;
import com.ironfit.backendmongo.usuario.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String login(String email, String passwordPlano) {
        UsuarioDoc u = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!u.isActivo()) throw new RuntimeException("Usuario inactivo");

        if (!passwordEncoder.matches(passwordPlano, u.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return jwtService.generarToken(u.getId(), u.getEmail(), u.getRoles());
    }
}
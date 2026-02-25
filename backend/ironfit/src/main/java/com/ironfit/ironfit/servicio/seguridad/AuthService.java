package com.ironfit.ironfit.servicio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.Usuario;
import com.ironfit.ironfit.repositorio.seguridad.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Autentica un usuario por usuario_login y clave en texto plano.
     * (En producción debería usarse cifrado de contraseña).
     */
    public Optional<Usuario> autenticar(String usuarioLogin, String clave) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuarioLogin(usuarioLogin);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getClave().equals(clave) && Boolean.TRUE.equals(usuario.getActivo())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }
}

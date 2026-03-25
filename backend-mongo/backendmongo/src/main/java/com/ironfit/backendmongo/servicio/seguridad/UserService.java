package com.ironfit.backendmongo.servicio.seguridad;

import com.ironfit.backendmongo.dto.seguridad.RegisterRequest;
import com.ironfit.backendmongo.dto.seguridad.UserResponse;
import com.ironfit.backendmongo.modelo.seguridad.UserDocument;
import com.ironfit.backendmongo.repositorio.seguridad.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repositorioUsuario;
    private final PasswordEncoder passwordEncoder;
    private static final String PASSWORD_TEMPORAL = "Ironfit123*";

    public UserService(UserRepository repositorioUsuario, PasswordEncoder passwordEncoder) {
        this.repositorioUsuario = repositorioUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse crearUsuario(RegisterRequest solicitud) {
        if (repositorioUsuario.existsByCorreo(solicitud.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con ese correo");
        }

        UserDocument usuario = new UserDocument();
        usuario.setCorreo(solicitud.getCorreo());
        usuario.setPasswordHash(passwordEncoder.encode(solicitud.getPassword()));
        usuario.setRoles(solicitud.getRoles());
        usuario.setActivo(true);
        usuario.setNombres(solicitud.getNombres());
        usuario.setApellidos(solicitud.getApellidos());
        usuario.setTelefono(solicitud.getTelefono());
        usuario.setTipoDoc(solicitud.getTipoDoc());
        usuario.setNumDoc(solicitud.getNumDoc());

        UserDocument guardado = repositorioUsuario.save(usuario);
        return convertirAResponse(guardado);
    }

    public List<UserResponse> listarUsuarios() {
        return repositorioUsuario.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public UserResponse buscarPorCorreo(String correo) {
        UserDocument usuario = repositorioUsuario.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return convertirAResponse(usuario);
    }

    public UserResponse cambiarEstado(String id, boolean activo) {
        UserDocument usuario = repositorioUsuario.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(activo);
        UserDocument guardado = repositorioUsuario.save(usuario);

        return convertirAResponse(guardado);
    }

    private UserResponse convertirAResponse(UserDocument usuario) {
        UserResponse respuesta = new UserResponse();
        respuesta.setId(usuario.getId());
        respuesta.setCorreo(usuario.getCorreo());
        respuesta.setRoles(usuario.getRoles());
        respuesta.setActivo(usuario.isActivo());
        respuesta.setNombres(usuario.getNombres());
        respuesta.setApellidos(usuario.getApellidos());
        respuesta.setTelefono(usuario.getTelefono());
        respuesta.setTipoDoc(usuario.getTipoDoc());
        respuesta.setNumDoc(usuario.getNumDoc());
        return respuesta;
    }

    public UserResponse actualizarMiPerfil(String correo, UserResponse solicitud) {
        UserDocument usuario = repositorioUsuario.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombres(solicitud.getNombres());
        usuario.setApellidos(solicitud.getApellidos());
        usuario.setTelefono(solicitud.getTelefono());
        usuario.setCorreo(solicitud.getCorreo());

        UserDocument guardado = repositorioUsuario.save(usuario);

        return convertirAResponse(guardado);
    }

    public void cambiarMiPassword(String correo, String nuevaPassword) {
        UserDocument usuario = repositorioUsuario.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setPasswordHash(passwordEncoder.encode(nuevaPassword));

        repositorioUsuario.save(usuario);
    }

    public void resetearPassword(String id) {
        UserDocument usuario = repositorioUsuario.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setPasswordHash(passwordEncoder.encode(PASSWORD_TEMPORAL));

        repositorioUsuario.save(usuario);
    }

    public List<UserResponse> buscarClientes(String texto) {
    return repositorioUsuario.findAll()
            .stream()
            .filter(usuario -> usuario.getRoles().contains("CLIENTE"))
            .filter(usuario ->
                    (usuario.getNombres() != null && usuario.getNombres().toLowerCase().contains(texto.toLowerCase())) ||
                    (usuario.getNumDoc() != null && usuario.getNumDoc().contains(texto))
            )
            .map(this::convertirAResponse)
            .toList();
}
}
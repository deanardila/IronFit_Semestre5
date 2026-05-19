package com.ironfit.backendmongo.servicio.asignaciones;

import com.ironfit.backendmongo.dto.asignaciones.AsignacionEntrenadorClienteRequest;
import com.ironfit.backendmongo.dto.asignaciones.AsignacionEntrenadorClienteResponse;
import com.ironfit.backendmongo.modelo.asignaciones.AsignacionEntrenadorCliente;
import com.ironfit.backendmongo.modelo.seguridad.RoleName;
import com.ironfit.backendmongo.modelo.seguridad.UserDocument;
import com.ironfit.backendmongo.repositorio.asignaciones.AsignacionEntrenadorClienteRepository;
import com.ironfit.backendmongo.repositorio.seguridad.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AsignacionEntrenadorClienteService {

    private final AsignacionEntrenadorClienteRepository asignacionRepository;
    private final UserRepository userRepository;

    public AsignacionEntrenadorClienteService(
            AsignacionEntrenadorClienteRepository asignacionRepository,
            UserRepository userRepository
    ) {
        this.asignacionRepository = asignacionRepository;
        this.userRepository = userRepository;
    }

    public List<AsignacionEntrenadorClienteResponse> listarAsignaciones() {
        return asignacionRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public List<AsignacionEntrenadorClienteResponse> listarMisClientes(Authentication authentication) {
        UserDocument usuarioActual = obtenerUsuarioAutenticado(authentication);

        validarRol(usuarioActual, RoleName.ENTRENADOR, "Solo el entrenador puede consultar sus clientes asignados");

        return asignacionRepository.findByEntrenadorIdAndActivoTrue(usuarioActual.getId())
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public AsignacionEntrenadorClienteResponse crearAsignacion(AsignacionEntrenadorClienteRequest request) {
        validarRequest(request);

        UserDocument cliente = userRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        UserDocument entrenador = userRepository.findById(request.getEntrenadorId())
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        validarUsuarioRolActivo(cliente, RoleName.CLIENTE, "El usuario seleccionado no es un cliente válido");
        validarUsuarioRolActivo(entrenador, RoleName.ENTRENADOR, "El usuario seleccionado no es un entrenador válido");

        boolean yaExiste = asignacionRepository.existsByClienteIdAndEntrenadorIdAndActivoTrue(
                cliente.getId(),
                entrenador.getId()
        );

        if (yaExiste) {
            throw new RuntimeException("Este cliente ya está asignado a este entrenador");
        }

        LocalDateTime ahora = LocalDateTime.now();

        AsignacionEntrenadorCliente asignacion = new AsignacionEntrenadorCliente();
        asignacion.setClienteId(cliente.getId());
        asignacion.setEntrenadorId(entrenador.getId());
        asignacion.setActivo(true);
        asignacion.setFechaAsignacion(ahora);
        asignacion.setFechaActualizacion(ahora);

        AsignacionEntrenadorCliente guardada = asignacionRepository.save(asignacion);

        return convertirAResponse(guardada);
    }

    public AsignacionEntrenadorClienteResponse cambiarEstado(String id, Boolean activo) {
        if (activo == null) {
            throw new RuntimeException("El estado es obligatorio");
        }

        AsignacionEntrenadorCliente asignacion = asignacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        asignacion.setActivo(activo);
        asignacion.setFechaActualizacion(LocalDateTime.now());

        AsignacionEntrenadorCliente actualizada = asignacionRepository.save(asignacion);

        return convertirAResponse(actualizada);
    }

    public boolean clienteAsignadoAEntrenador(String clienteId, String entrenadorId) {
        return asignacionRepository.existsByClienteIdAndEntrenadorIdAndActivoTrue(clienteId, entrenadorId);
    }

    private void validarRequest(AsignacionEntrenadorClienteRequest request) {
        if (request == null) {
            throw new RuntimeException("Los datos de la asignación son obligatorios");
        }

        if (esTextoVacio(request.getClienteId())) {
            throw new RuntimeException("El cliente es obligatorio");
        }

        if (esTextoVacio(request.getEntrenadorId())) {
            throw new RuntimeException("El entrenador es obligatorio");
        }
    }

    private void validarUsuarioRolActivo(UserDocument usuario, String rol, String mensajeRol) {
        if (usuario.getRoles() == null || !usuario.getRoles().contains(rol)) {
            throw new RuntimeException(mensajeRol);
        }

        if (!usuario.isActivo()) {
            throw new RuntimeException("El usuario seleccionado está inactivo");
        }
    }

    private void validarRol(UserDocument usuario, String rol, String mensaje) {
        if (usuario.getRoles() == null || !usuario.getRoles().contains(rol)) {
            throw new RuntimeException(mensaje);
        }
    }

    private UserDocument obtenerUsuarioAutenticado(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No se pudo identificar el usuario autenticado");
        }

        return userRepository.findByCorreo(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
    }

    private AsignacionEntrenadorClienteResponse convertirAResponse(AsignacionEntrenadorCliente asignacion) {
        AsignacionEntrenadorClienteResponse response = new AsignacionEntrenadorClienteResponse();

        response.setId(asignacion.getId());
        response.setClienteId(asignacion.getClienteId());
        response.setEntrenadorId(asignacion.getEntrenadorId());
        response.setActivo(asignacion.getActivo());
        response.setFechaAsignacion(asignacion.getFechaAsignacion());
        response.setFechaActualizacion(asignacion.getFechaActualizacion());

        userRepository.findById(asignacion.getClienteId()).ifPresent(cliente -> {
            response.setClienteNombre(construirNombreCompleto(cliente));
            response.setClienteCorreo(cliente.getCorreo());
            response.setClienteDocumento(cliente.getNumDoc());
        });

        userRepository.findById(asignacion.getEntrenadorId()).ifPresent(entrenador -> {
            response.setEntrenadorNombre(construirNombreCompleto(entrenador));
            response.setEntrenadorCorreo(entrenador.getCorreo());
        });

        return response;
    }

    private String construirNombreCompleto(UserDocument usuario) {
        String nombres = usuario.getNombres() != null ? usuario.getNombres().trim() : "";
        String apellidos = usuario.getApellidos() != null ? usuario.getApellidos().trim() : "";
        return (nombres + " " + apellidos).trim();
    }

    private boolean esTextoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}
package com.ironfit.backendmongo.servicio.planes;

import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoActualizarRequest;
import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoCrearRequest;
import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoResponse;
import com.ironfit.backendmongo.modelo.planes.PlanEntrenamiento;
import com.ironfit.backendmongo.modelo.seguridad.RoleName;
import com.ironfit.backendmongo.modelo.seguridad.UserDocument;
import com.ironfit.backendmongo.repositorio.planes.PlanEntrenamientoRepository;
import com.ironfit.backendmongo.repositorio.seguridad.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlanEntrenamientoService {

    private final PlanEntrenamientoRepository planEntrenamientoRepository;
    private final UserRepository userRepository;

    public PlanEntrenamientoService(
            PlanEntrenamientoRepository planEntrenamientoRepository,
            UserRepository userRepository
    ) {
        this.planEntrenamientoRepository = planEntrenamientoRepository;
        this.userRepository = userRepository;
    }

    public List<PlanEntrenamientoResponse> listarPlanes(Authentication authentication) {
        UserDocument usuarioActual = obtenerUsuarioAutenticado(authentication);

        List<PlanEntrenamiento> planes;

        if (tieneRol(usuarioActual, RoleName.ADMIN)) {

            planes = planEntrenamientoRepository.findAllByOrderByFechaCreacionDesc();

        } else if (tieneRol(usuarioActual, RoleName.ENTRENADOR)) {

            planes = planEntrenamientoRepository
                    .findByEntrenadorIdOrderByFechaCreacionDesc(usuarioActual.getId());

        } else if (tieneRol(usuarioActual, RoleName.CLIENTE)) {

            planes = planEntrenamientoRepository
                    .findByClienteIdOrderByFechaCreacionDesc(usuarioActual.getId());

        } else {
            throw new RuntimeException("No tienes permisos para listar planes");
        }

        return planes.stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public PlanEntrenamientoResponse crearPlan(
            Authentication authentication,
            PlanEntrenamientoCrearRequest request
    ) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        UserDocument usuarioActual = obtenerUsuarioAutenticado(authentication);

        String nombreLimpio = request.getNombre().trim();
        String descripcionLimpia = request.getDescripcion().trim();
        String objetivoLimpio = request.getObjetivo().trim();
        String clienteIdLimpio = request.getClienteId().trim();
        String entrenadorIdFinal = resolverEntrenadorId(usuarioActual, request.getEntrenadorId());

        validarCliente(clienteIdLimpio);
        validarEntrenador(entrenadorIdFinal);
        validarClienteSinPlanActivo(clienteIdLimpio, null);

        LocalDateTime ahora = LocalDateTime.now();

        PlanEntrenamiento plan = new PlanEntrenamiento();
        plan.setNombre(nombreLimpio);
        plan.setDescripcion(descripcionLimpia);
        plan.setObjetivo(objetivoLimpio);
        plan.setFechaInicio(request.getFechaInicio());
        plan.setFechaFin(request.getFechaFin());
        plan.setClienteId(clienteIdLimpio);
        plan.setEntrenadorId(entrenadorIdFinal);
        plan.setActivo(true);
        plan.setFechaCreacion(ahora);
        plan.setFechaActualizacion(ahora);

        PlanEntrenamiento guardado = planEntrenamientoRepository.save(plan);
        return convertirAResponse(guardado);
    }

    public PlanEntrenamientoResponse actualizarPlan(
            Authentication authentication,
            String id,
            PlanEntrenamientoActualizarRequest request
    ) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        UserDocument usuarioActual = obtenerUsuarioAutenticado(authentication);

        PlanEntrenamiento plan = planEntrenamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de entrenamiento no encontrado"));

        validarAccesoAlPlan(usuarioActual, plan);

        String nombreLimpio = request.getNombre().trim();
        String descripcionLimpia = request.getDescripcion().trim();
        String objetivoLimpio = request.getObjetivo().trim();
        String clienteIdLimpio = request.getClienteId().trim();
        String entrenadorIdFinal = resolverEntrenadorId(usuarioActual, request.getEntrenadorId());

        validarCliente(clienteIdLimpio);
        validarEntrenador(entrenadorIdFinal);

        boolean cambioCliente = !clienteIdLimpio.equals(plan.getClienteId());

        if (cambioCliente && Boolean.TRUE.equals(plan.getActivo())) {
            validarClienteSinPlanActivo(clienteIdLimpio, plan.getId());
        }

        plan.setNombre(nombreLimpio);
        plan.setDescripcion(descripcionLimpia);
        plan.setObjetivo(objetivoLimpio);
        plan.setFechaInicio(request.getFechaInicio());
        plan.setFechaFin(request.getFechaFin());
        plan.setClienteId(clienteIdLimpio);
        plan.setEntrenadorId(entrenadorIdFinal);
        plan.setFechaActualizacion(LocalDateTime.now());

        PlanEntrenamiento actualizado = planEntrenamientoRepository.save(plan);
        return convertirAResponse(actualizado);
    }

    public PlanEntrenamientoResponse cambiarEstado(
        Authentication authentication,
        String id,
        Boolean activo
        ) {
            UserDocument usuarioActual = obtenerUsuarioAutenticado(authentication);

            PlanEntrenamiento plan = planEntrenamientoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Plan de entrenamiento no encontrado"));

            validarAccesoAlPlan(usuarioActual, plan);

            if (Boolean.TRUE.equals(activo)) {
                validarClienteSinPlanActivo(plan.getClienteId(), plan.getId());
            }

            plan.setActivo(activo);
            plan.setFechaActualizacion(LocalDateTime.now());

            PlanEntrenamiento actualizado = planEntrenamientoRepository.save(plan);

        return convertirAResponse(actualizado);
    }


    private void validarClienteSinPlanActivo(String clienteId, String planIdActual) {
        List<PlanEntrenamiento> planesActivos =
                planEntrenamientoRepository.findByClienteIdAndActivoTrue(clienteId);

        boolean tieneOtroPlanActivo = planesActivos.stream()
                .anyMatch(plan -> planIdActual == null || !plan.getId().equals(planIdActual));

        if (tieneOtroPlanActivo) {
            throw new RuntimeException("Este cliente ya tiene un plan activo asignado");
        }
    }

    private UserDocument obtenerUsuarioAutenticado(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No se pudo identificar el usuario autenticado");
        }

        String correo = authentication.getName();

        return userRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
    }

    private boolean tieneRol(UserDocument usuario, String rol) {
        return usuario.getRoles() != null && usuario.getRoles().contains(rol);
    }

    private String resolverEntrenadorId(UserDocument usuarioActual, String entrenadorIdRequest) {
        if (tieneRol(usuarioActual, RoleName.ENTRENADOR)) {
            return usuarioActual.getId();
        }

        if (tieneRol(usuarioActual, RoleName.ADMIN)) {
            if (entrenadorIdRequest == null || entrenadorIdRequest.trim().isEmpty()) {
                throw new RuntimeException("El entrenadorId es obligatorio para el administrador");
            }
            return entrenadorIdRequest.trim();
        }

        throw new RuntimeException("No tienes permisos para asignar entrenador");
    }

    private void validarCliente(String clienteId) {
        UserDocument cliente = userRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (cliente.getRoles() == null || !cliente.getRoles().contains(RoleName.CLIENTE)) {
            throw new RuntimeException("El usuario seleccionado no es un cliente válido");
        }

        if (!cliente.isActivo()) {
            throw new RuntimeException("El cliente seleccionado está inactivo");
        }
    }

    private void validarEntrenador(String entrenadorId) {
        UserDocument entrenador = userRepository.findById(entrenadorId)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));

        if (entrenador.getRoles() == null || !entrenador.getRoles().contains(RoleName.ENTRENADOR)) {
            throw new RuntimeException("El usuario seleccionado no es un entrenador válido");
        }

        if (!entrenador.isActivo()) {
            throw new RuntimeException("El entrenador seleccionado está inactivo");
        }
    }

    private void validarAccesoAlPlan(UserDocument usuarioActual, PlanEntrenamiento plan) {
        if (tieneRol(usuarioActual, RoleName.ADMIN)) {
            return;
        }

        if (tieneRol(usuarioActual, RoleName.ENTRENADOR)
                && usuarioActual.getId().equals(plan.getEntrenadorId())) {
            return;
        }

        throw new RuntimeException("No tienes permisos para modificar este plan");
    }

    private void validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new RuntimeException("Las fechas de inicio y fin son obligatorias");
        }

        if (fechaFin.isBefore(fechaInicio)) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }
    }

    private PlanEntrenamientoResponse convertirAResponse(PlanEntrenamiento plan) {
        PlanEntrenamientoResponse response = new PlanEntrenamientoResponse();
        response.setId(plan.getId());
        response.setNombre(plan.getNombre());
        response.setDescripcion(plan.getDescripcion());
        response.setObjetivo(plan.getObjetivo());
        response.setFechaInicio(plan.getFechaInicio());
        response.setFechaFin(plan.getFechaFin());
        response.setClienteId(plan.getClienteId());
        response.setEntrenadorId(plan.getEntrenadorId());
        response.setActivo(plan.getActivo());
        response.setFechaCreacion(plan.getFechaCreacion());
        response.setFechaActualizacion(plan.getFechaActualizacion());

        userRepository.findById(plan.getClienteId()).ifPresent(cliente ->
                response.setClienteNombre(construirNombreCompleto(cliente)));

        userRepository.findById(plan.getEntrenadorId()).ifPresent(entrenador ->
                response.setEntrenadorNombre(construirNombreCompleto(entrenador)));

        return response;
    }

    private String construirNombreCompleto(UserDocument usuario) {
        String nombres = usuario.getNombres() != null ? usuario.getNombres().trim() : "";
        String apellidos = usuario.getApellidos() != null ? usuario.getApellidos().trim() : "";
        return (nombres + " " + apellidos).trim();
    }
}
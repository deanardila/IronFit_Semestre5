package com.ironfit.backendmongo.servicio.asistencias;

import com.ironfit.backendmongo.dto.asistencias.AsistenciaReporteResponse;
import com.ironfit.backendmongo.modelo.asistencias.Asistencia;
import com.ironfit.backendmongo.modelo.seguridad.RoleName;
import com.ironfit.backendmongo.modelo.seguridad.UserDocument;
import com.ironfit.backendmongo.repositorio.asistencias.AsistenciaRepository;
import com.ironfit.backendmongo.repositorio.planes.PlanEntrenamientoRepository;
import com.ironfit.backendmongo.repositorio.rutinas.RutinaRepository;
import com.ironfit.backendmongo.repositorio.seguridad.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final PlanEntrenamientoRepository planRepository;
    private final RutinaRepository rutinaRepository;
    private final UserRepository userRepository;

    public AsistenciaService(
            AsistenciaRepository asistenciaRepository,
            PlanEntrenamientoRepository planRepository,
            RutinaRepository rutinaRepository,
            UserRepository userRepository
    ) {
        this.asistenciaRepository = asistenciaRepository;
        this.planRepository = planRepository;
        this.rutinaRepository = rutinaRepository;
        this.userRepository = userRepository;
    }

    public List<AsistenciaReporteResponse> generarReporte(
            Authentication authentication,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            String clienteId,
            String entrenadorId,
            String estado
    ) {
        UserDocument usuarioActual = obtenerUsuarioAutenticado(authentication);

        List<Asistencia> asistencias;

        if (fechaInicio != null && fechaFin != null) {
            if (fechaFin.isBefore(fechaInicio)) {
                throw new RuntimeException("La fecha final no puede ser anterior a la fecha inicial");
            }

            asistencias = asistenciaRepository.findByFechaAsistenciaBetweenOrderByFechaAsistenciaDesc(
                    fechaInicio,
                    fechaFin
            );
        } else {
            asistencias = asistenciaRepository.findAllByOrderByFechaAsistenciaDesc();
        }

        return asistencias.stream()
                .filter(asistencia -> filtrarPorRol(usuarioActual, asistencia))
                .filter(asistencia -> filtrarPorCliente(asistencia, clienteId))
                .filter(asistencia -> filtrarPorEntrenador(asistencia, entrenadorId, usuarioActual))
                .filter(asistencia -> filtrarPorEstado(asistencia, estado))
                .map(this::convertirAResponse)
                .toList();
    }

    private boolean filtrarPorRol(UserDocument usuarioActual, Asistencia asistencia) {
        if (tieneRol(usuarioActual, RoleName.ADMIN)) {
            return true;
        }

        if (tieneRol(usuarioActual, RoleName.ENTRENADOR)) {
            return planRepository.findById(asistencia.getPlanId())
                    .map(plan -> usuarioActual.getId().equals(plan.getEntrenadorId()))
                    .orElse(false);
        }

        throw new RuntimeException("No tienes permisos para consultar reportes de asistencia");
    }

    private boolean filtrarPorCliente(Asistencia asistencia, String clienteId) {
        if (clienteId == null || clienteId.trim().isEmpty()) {
            return true;
        }

        return clienteId.trim().equals(asistencia.getClienteId());
    }

    private boolean filtrarPorEntrenador(
            Asistencia asistencia,
            String entrenadorId,
            UserDocument usuarioActual
    ) {
        if (tieneRol(usuarioActual, RoleName.ENTRENADOR)) {
            return true;
        }

        if (entrenadorId == null || entrenadorId.trim().isEmpty()) {
            return true;
        }

        return planRepository.findById(asistencia.getPlanId())
                .map(plan -> entrenadorId.trim().equals(plan.getEntrenadorId()))
                .orElse(false);
    }

    private boolean filtrarPorEstado(Asistencia asistencia, String estado) {
        if (estado == null || estado.trim().isEmpty() || "TODOS".equalsIgnoreCase(estado)) {
            return true;
        }

        String estadoCalculado = calcularEstadoAsistencia(asistencia)
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace(" ", "_")
                .toUpperCase();

        String estadoFiltro = estado.trim()
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace(" ", "_")
                .toUpperCase();

        return estadoCalculado.equals(estadoFiltro);
    }

    private AsistenciaReporteResponse convertirAResponse(Asistencia asistencia) {
        AsistenciaReporteResponse response = new AsistenciaReporteResponse();

        response.setId(asistencia.getId());
        response.setClienteId(asistencia.getClienteId());
        response.setPlanId(asistencia.getPlanId());
        response.setRutinaId(asistencia.getRutinaId());
        response.setFechaAsistencia(asistencia.getFechaAsistencia());
        response.setAsistio(asistencia.getAsistio());
        response.setCumplioRutina(asistencia.getCumplioRutina());
        response.setEstadoAsistencia(calcularEstadoAsistencia(asistencia));
        response.setObservaciones(asistencia.getObservaciones());

        userRepository.findById(asistencia.getClienteId()).ifPresent(cliente -> {
            response.setClienteNombre(construirNombreCompleto(cliente));
            response.setClienteDocumento(cliente.getNumDoc());
        });

        planRepository.findById(asistencia.getPlanId()).ifPresent(plan -> {
            response.setPlanNombre(plan.getNombre());
            response.setEntrenadorId(plan.getEntrenadorId());

            userRepository.findById(plan.getEntrenadorId()).ifPresent(entrenador ->
                    response.setEntrenadorNombre(construirNombreCompleto(entrenador))
            );
        });

        rutinaRepository.findById(asistencia.getRutinaId()).ifPresent(rutina ->
                response.setRutinaNombre(rutina.getNombre())
        );

        return response;
    }

    private String calcularEstadoAsistencia(Asistencia asistencia) {
        if (!Boolean.TRUE.equals(asistencia.getAsistio())) {
            return "NO ASISTIÓ";
        }

        if (Boolean.TRUE.equals(asistencia.getCumplioRutina())) {
            return "ASISTIÓ";
        }

        return "ASISTIÓ SIN RUTINA";
    }

    private UserDocument obtenerUsuarioAutenticado(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No se pudo identificar el usuario autenticado");
        }

        return userRepository.findByCorreo(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));
    }

    private boolean tieneRol(UserDocument usuario, String rol) {
        return usuario.getRoles() != null && usuario.getRoles().contains(rol);
    }

    private String construirNombreCompleto(UserDocument usuario) {
        String nombres = usuario.getNombres() != null ? usuario.getNombres().trim() : "";
        String apellidos = usuario.getApellidos() != null ? usuario.getApellidos().trim() : "";
        return (nombres + " " + apellidos).trim();
    }
}
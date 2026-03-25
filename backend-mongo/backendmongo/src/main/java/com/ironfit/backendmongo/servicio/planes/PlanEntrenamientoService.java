package com.ironfit.backendmongo.servicio.planes;

import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoActualizarRequest;
import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoCrearRequest;
import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoResponse;
import com.ironfit.backendmongo.modelo.planes.PlanEntrenamiento;
import com.ironfit.backendmongo.repositorio.planes.PlanEntrenamientoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PlanEntrenamientoService {

    private final PlanEntrenamientoRepository planEntrenamientoRepository;

    public PlanEntrenamientoService(PlanEntrenamientoRepository planEntrenamientoRepository) {
        this.planEntrenamientoRepository = planEntrenamientoRepository;
    }

    public List<PlanEntrenamientoResponse> listarPlanes() {
        return planEntrenamientoRepository.findByActivoTrue()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public PlanEntrenamientoResponse crearPlan(PlanEntrenamientoCrearRequest request) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        LocalDateTime ahora = LocalDateTime.now();

        PlanEntrenamiento plan = new PlanEntrenamiento();
        plan.setNombre(request.getNombre().trim());
        plan.setDescripcion(request.getDescripcion().trim());
        plan.setObjetivo(request.getObjetivo().trim());
        plan.setFechaInicio(request.getFechaInicio());
        plan.setFechaFin(request.getFechaFin());
        plan.setClienteId(request.getClienteId().trim());
        plan.setEntrenadorId(request.getEntrenadorId().trim());
        plan.setActivo(true);
        plan.setFechaCreacion(ahora);
        plan.setFechaActualizacion(ahora);

        PlanEntrenamiento guardado = planEntrenamientoRepository.save(plan);
        return convertirAResponse(guardado);
    }

    public PlanEntrenamientoResponse actualizarPlan(String id, PlanEntrenamientoActualizarRequest request) {
        validarFechas(request.getFechaInicio(), request.getFechaFin());

        PlanEntrenamiento plan = planEntrenamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de entrenamiento no encontrado"));

        plan.setNombre(request.getNombre().trim());
        plan.setDescripcion(request.getDescripcion().trim());
        plan.setObjetivo(request.getObjetivo().trim());
        plan.setFechaInicio(request.getFechaInicio());
        plan.setFechaFin(request.getFechaFin());
        plan.setClienteId(request.getClienteId().trim());
        plan.setEntrenadorId(request.getEntrenadorId().trim());
        plan.setFechaActualizacion(LocalDateTime.now());

        PlanEntrenamiento actualizado = planEntrenamientoRepository.save(plan);
        return convertirAResponse(actualizado);
    }

    public void cambiarEstado(String id, Boolean activo) {
        PlanEntrenamiento plan = planEntrenamientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de entrenamiento no encontrado"));

        plan.setActivo(activo);
        plan.setFechaActualizacion(LocalDateTime.now());

        planEntrenamientoRepository.save(plan);
    }

    private void validarFechas(java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin) {
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
        return response;
    }
}
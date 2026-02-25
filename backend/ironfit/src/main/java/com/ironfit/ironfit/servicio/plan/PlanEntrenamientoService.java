package com.ironfit.ironfit.servicio.plan;

import com.ironfit.ironfit.dto.PlanResumenDTO;
import com.ironfit.ironfit.modelo.plan.EstadoPlan;
import com.ironfit.ironfit.modelo.plan.ObjetivoPlan;
import com.ironfit.ironfit.modelo.plan.PlanEntrenamiento;
import com.ironfit.ironfit.repositorio.plan.EstadoPlanRepository;
import com.ironfit.ironfit.repositorio.plan.ObjetivoPlanRepository;
import com.ironfit.ironfit.repositorio.plan.PlanEntrenamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PlanEntrenamientoService {

    private final PlanEntrenamientoRepository planEntrenamientoRepository;
    private final ObjetivoPlanRepository objetivoPlanRepository;
    private final EstadoPlanRepository estadoPlanRepository;

    public PlanEntrenamientoService(PlanEntrenamientoRepository planEntrenamientoRepository,
                                    ObjetivoPlanRepository objetivoPlanRepository,
                                    EstadoPlanRepository estadoPlanRepository) {
        this.planEntrenamientoRepository = planEntrenamientoRepository;
        this.objetivoPlanRepository = objetivoPlanRepository;
        this.estadoPlanRepository = estadoPlanRepository;
    }

    

    // -------------------- CRUD BÁSICO --------------------

    public List<PlanEntrenamiento> listarTodos() {
        return planEntrenamientoRepository.findAll();
    }

    public Optional<PlanEntrenamiento> buscarPorId(Long idPlan) {
        return planEntrenamientoRepository.findById(idPlan);
    }

    public PlanEntrenamiento guardar(PlanEntrenamiento plan) {
        return planEntrenamientoRepository.save(plan);
    }

    public void eliminar(Long idPlan) {
        planEntrenamientoRepository.deleteById(idPlan);
    }

    // -------------------- CONSULTAS POR USUARIO --------------------

    public List<PlanEntrenamiento> listarPorCliente(Long idUsuarioCliente) {
        return planEntrenamientoRepository.findByUsuarioCliente_IdUsuario(idUsuarioCliente);
    }

    public List<PlanEntrenamiento> listarPorEntrenador(Long idUsuarioEntrenador) {
        return planEntrenamientoRepository.findByUsuarioEntrenador_IdUsuario(idUsuarioEntrenador);
    }

    // -------------------- OBJETIVO Y ESTADO --------------------

    public void cambiarObjetivo(Long idPlan, Long idObjetivo) {
        PlanEntrenamiento plan = planEntrenamientoRepository.findById(idPlan)
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado: " + idPlan));

        ObjetivoPlan obj = objetivoPlanRepository.findById(idObjetivo)
                .orElseThrow(() -> new IllegalArgumentException("Objetivo no encontrado: " + idObjetivo));

        plan.setObjetivo(obj);
        planEntrenamientoRepository.save(plan);
    }

    public void cambiarEstado(Long idPlan, Long idEstadoPlan) {
        PlanEntrenamiento plan = planEntrenamientoRepository.findById(idPlan)
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado: " + idPlan));

        EstadoPlan estado = estadoPlanRepository.findById(idEstadoPlan)
                .orElseThrow(() -> new IllegalArgumentException("Estado de plan no encontrado: " + idEstadoPlan));

        plan.setEstadoPlan(estado);
        planEntrenamientoRepository.save(plan);
    }

    public List<PlanResumenDTO> listarPlanesResumen() {
        List<PlanEntrenamiento> planes = planEntrenamientoRepository.findAll();

        return planes.stream()
                .map(p -> {
                    var cliente = p.getUsuarioCliente();
                    var entrenador = p.getUsuarioEntrenador();

                    String nombreCliente = null;
                    Long idCliente = null;
                    if (cliente != null) {
                        idCliente = cliente.getIdUsuario();
                        nombreCliente = cliente.getNombres() + " " + cliente.getApellidos();
                    }

                    String nombreEntrenador = null;
                    Long idEntrenador = null;
                    if (entrenador != null) {
                        idEntrenador = entrenador.getIdUsuario();
                        nombreEntrenador = entrenador.getNombres() + " " + entrenador.getApellidos();
                    }

                    // Representación sencilla del estado (evitamos adivinar el nombre del campo)
                    String estado = (p.getEstadoPlan() != null)
                            ? p.getEstadoPlan().toString()
                            : null;

                    return new PlanResumenDTO(
                            p.getIdPlan(),
                            p.getNombre(),
                            estado,
                            p.getFechaInicio() != null ? p.getFechaInicio().toString() : null,
                            p.getFechaFin() != null ? p.getFechaFin().toString() : null,
                            idCliente,
                            nombreCliente,
                            idEntrenador,
                            nombreEntrenador
                    );
                })
                .toList();
    

    }
}

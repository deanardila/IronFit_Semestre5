package com.ironfit.ironfit.servicio.plan;

import com.ironfit.ironfit.modelo.plan.PlanNutricion;
import com.ironfit.ironfit.repositorio.plan.PlanNutricionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanNutricionService {

    private final PlanNutricionRepository planNutricionRepository;

    public PlanNutricionService(PlanNutricionRepository planNutricionRepository) {
        this.planNutricionRepository = planNutricionRepository;
    }

    public List<PlanNutricion> listarTodos() {
        return planNutricionRepository.findAll();
    }

    public Optional<PlanNutricion> buscarPorId(Long idNutricion) {
        return planNutricionRepository.findById(idNutricion);
    }

    public List<PlanNutricion> listarPorPlanEntrenamiento(Long idPlan) {
        return planNutricionRepository.findByPlanEntrenamiento_IdPlan(idPlan);
    }

    public PlanNutricion guardar(PlanNutricion planNutricion) {
        return planNutricionRepository.save(planNutricion);
    }

    public void eliminar(Long idNutricion) {
        planNutricionRepository.deleteById(idNutricion);
    }
}

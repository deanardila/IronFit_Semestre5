package com.ironfit.ironfit.repositorio.plan;

import com.ironfit.ironfit.modelo.plan.PlanNutricion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanNutricionRepository extends JpaRepository<PlanNutricion, Long> {

    List<PlanNutricion> findByPlanEntrenamiento_IdPlan(Long idPlan);
}

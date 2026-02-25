package com.ironfit.ironfit.repositorio.rutina;

import com.ironfit.ironfit.modelo.rutina.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {

    List<Rutina> findByPlanEntrenamiento_IdPlan(Long idPlan);
}

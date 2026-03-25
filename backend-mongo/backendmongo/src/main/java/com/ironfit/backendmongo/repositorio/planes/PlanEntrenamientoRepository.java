package com.ironfit.backendmongo.repositorio.planes;

import com.ironfit.backendmongo.modelo.planes.PlanEntrenamiento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlanEntrenamientoRepository extends MongoRepository<PlanEntrenamiento, String> {

    List<PlanEntrenamiento> findByActivoTrue();

    List<PlanEntrenamiento> findByEntrenadorIdAndActivoTrue(String entrenadorId);

    List<PlanEntrenamiento> findByClienteIdAndActivoTrue(String clienteId);
}
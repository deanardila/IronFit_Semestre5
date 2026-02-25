package com.ironfit.ironfit.repositorio.plan;

import com.ironfit.ironfit.modelo.plan.PlanEntrenamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanEntrenamientoRepository extends JpaRepository<PlanEntrenamiento, Long> {

    // Planes por cliente (usuario que es cliente)
    List<PlanEntrenamiento> findByUsuarioCliente_IdUsuario(Long idUsuarioCliente);

    // Planes por entrenador (usuario que es entrenador)
    List<PlanEntrenamiento> findByUsuarioEntrenador_IdUsuario(Long idUsuarioEntrenador);

    // ⛔ IMPORTANTE:
    // NO pongas aquí ningún método llamado listarPlanesResumen ni ninguna @Query personalizada.
}

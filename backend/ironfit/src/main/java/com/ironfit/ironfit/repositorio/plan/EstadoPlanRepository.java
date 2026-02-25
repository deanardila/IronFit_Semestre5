package com.ironfit.ironfit.repositorio.plan;

import com.ironfit.ironfit.modelo.plan.EstadoPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoPlanRepository extends JpaRepository<EstadoPlan, Long> {

    Optional<EstadoPlan> findByNombre(String nombre);
}

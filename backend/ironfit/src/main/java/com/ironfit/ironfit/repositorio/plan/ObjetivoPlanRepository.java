package com.ironfit.ironfit.repositorio.plan;

import com.ironfit.ironfit.modelo.plan.ObjetivoPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ObjetivoPlanRepository extends JpaRepository<ObjetivoPlan, Long> {

    Optional<ObjetivoPlan> findByNombre(String nombre);
}

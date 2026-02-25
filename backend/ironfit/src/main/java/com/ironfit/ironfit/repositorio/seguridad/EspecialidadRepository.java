package com.ironfit.ironfit.repositorio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {

    Optional<Especialidad> findByNombre(String nombre);
}

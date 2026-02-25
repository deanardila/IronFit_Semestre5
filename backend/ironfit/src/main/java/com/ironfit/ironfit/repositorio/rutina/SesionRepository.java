package com.ironfit.ironfit.repositorio.rutina;

import com.ironfit.ironfit.modelo.rutina.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SesionRepository extends JpaRepository<Sesion, Long> {

    List<Sesion> findByRutina_IdRutina(Long idRutina);
}

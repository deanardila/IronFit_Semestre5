package com.ironfit.ironfit.repositorio.rutina;

import com.ironfit.ironfit.modelo.rutina.RutinaEjercicio;
import com.ironfit.ironfit.modelo.rutina.RutinaEjercicioId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RutinaEjercicioRepository extends JpaRepository<RutinaEjercicio, RutinaEjercicioId> {

    List<RutinaEjercicio> findByRutina_IdRutina(Long idRutina);
}

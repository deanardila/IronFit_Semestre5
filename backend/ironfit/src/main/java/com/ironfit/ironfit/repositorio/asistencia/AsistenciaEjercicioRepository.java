package com.ironfit.ironfit.repositorio.asistencia;

import com.ironfit.ironfit.modelo.asistencia.AsistenciaEjercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsistenciaEjercicioRepository extends JpaRepository<AsistenciaEjercicio, Long> {

    List<AsistenciaEjercicio> findByAsistencia_IdAsistencia(Long idAsistencia);

    List<AsistenciaEjercicio> findByEjercicio_IdEjercicio(Long idEjercicio);
}

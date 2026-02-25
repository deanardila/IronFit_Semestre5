package com.ironfit.ironfit.repositorio.evaluacion;

import com.ironfit.ironfit.modelo.evaluacion.EvaluacionFisica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluacionFisicaRepository extends JpaRepository<EvaluacionFisica, Long> {

    List<EvaluacionFisica> findByUsuarioCliente_IdUsuario(Long idUsuarioCliente);

    List<EvaluacionFisica> findByUsuarioEvaluador_IdUsuario(Long idUsuarioEvaluador);
}

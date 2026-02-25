package com.ironfit.ironfit.servicio.evaluacion;

import com.ironfit.ironfit.modelo.evaluacion.EvaluacionFisica;
import com.ironfit.ironfit.repositorio.evaluacion.EvaluacionFisicaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EvaluacionFisicaService {

    private final EvaluacionFisicaRepository evaluacionFisicaRepository;

    public EvaluacionFisicaService(EvaluacionFisicaRepository evaluacionFisicaRepository) {
        this.evaluacionFisicaRepository = evaluacionFisicaRepository;
    }

    // -------------------- CRUD BÁSICO --------------------

    public List<EvaluacionFisica> listarTodas() {
        return evaluacionFisicaRepository.findAll();
    }

    public Optional<EvaluacionFisica> buscarPorId(Long idEval) {
        return evaluacionFisicaRepository.findById(idEval);
    }

    public EvaluacionFisica guardar(EvaluacionFisica evaluacion) {
        return evaluacionFisicaRepository.save(evaluacion);
    }

    public void eliminar(Long idEval) {
        evaluacionFisicaRepository.deleteById(idEval);
    }

    // -------------------- CONSULTAS POR USUARIO --------------------

    public List<EvaluacionFisica> listarPorCliente(Long idUsuarioCliente) {
        return evaluacionFisicaRepository.findByUsuarioCliente_IdUsuario(idUsuarioCliente);
    }

    public List<EvaluacionFisica> listarPorEvaluador(Long idUsuarioEvaluador) {
        return evaluacionFisicaRepository.findByUsuarioEvaluador_IdUsuario(idUsuarioEvaluador);
    }
}

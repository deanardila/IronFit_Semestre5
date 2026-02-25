package com.ironfit.ironfit.servicio.asistencia;

import com.ironfit.ironfit.modelo.asistencia.AsistenciaEjercicio;
import com.ironfit.ironfit.repositorio.asistencia.AsistenciaEjercicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaEjercicioService {

    private final AsistenciaEjercicioRepository asistenciaEjercicioRepository;

    public AsistenciaEjercicioService(AsistenciaEjercicioRepository asistenciaEjercicioRepository) {
        this.asistenciaEjercicioRepository = asistenciaEjercicioRepository;
    }

    public List<AsistenciaEjercicio> listarTodos() {
        return asistenciaEjercicioRepository.findAll();
    }

    public Optional<AsistenciaEjercicio> buscarPorId(Long id) {
        return asistenciaEjercicioRepository.findById(id);
    }

    public List<AsistenciaEjercicio> listarPorAsistencia(Long idAsistencia) {
        return asistenciaEjercicioRepository.findByAsistencia_IdAsistencia(idAsistencia);
    }

    public AsistenciaEjercicio guardar(AsistenciaEjercicio registro) {
        return asistenciaEjercicioRepository.save(registro);
    }

    public void eliminar(Long id) {
        asistenciaEjercicioRepository.deleteById(id);
    }
}

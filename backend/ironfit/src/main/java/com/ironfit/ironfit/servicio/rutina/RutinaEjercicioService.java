package com.ironfit.ironfit.servicio.rutina;

import com.ironfit.ironfit.modelo.rutina.RutinaEjercicio;
import com.ironfit.ironfit.modelo.rutina.RutinaEjercicioId;
import com.ironfit.ironfit.repositorio.rutina.RutinaEjercicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutinaEjercicioService {

    private final RutinaEjercicioRepository rutinaEjercicioRepository;

    public RutinaEjercicioService(RutinaEjercicioRepository rutinaEjercicioRepository) {
        this.rutinaEjercicioRepository = rutinaEjercicioRepository;
    }

    public List<RutinaEjercicio> listarPorRutina(Long idRutina) {
        return rutinaEjercicioRepository.findByRutina_IdRutina(idRutina);
    }

    public Optional<RutinaEjercicio> buscarPorId(RutinaEjercicioId id) {
        return rutinaEjercicioRepository.findById(id);
    }

    public RutinaEjercicio guardar(RutinaEjercicio rutinaEjercicio) {
        return rutinaEjercicioRepository.save(rutinaEjercicio);
    }

    public void eliminar(RutinaEjercicioId id) {
        rutinaEjercicioRepository.deleteById(id);
    }
}

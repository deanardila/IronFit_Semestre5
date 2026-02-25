package com.ironfit.ironfit.servicio.rutina;

import com.ironfit.ironfit.modelo.rutina.Sesion;
import com.ironfit.ironfit.repositorio.rutina.SesionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SesionService {

    private final SesionRepository sesionRepository;

    public SesionService(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    public List<Sesion> listarTodas() {
        return sesionRepository.findAll();
    }

    public Optional<Sesion> buscarPorId(Long idSesion) {
        return sesionRepository.findById(idSesion);
    }

    public List<Sesion> listarPorRutina(Long idRutina) {
        return sesionRepository.findByRutina_IdRutina(idRutina);
    }

    public Sesion guardar(Sesion sesion) {
        return sesionRepository.save(sesion);
    }

    public void eliminar(Long idSesion) {
        sesionRepository.deleteById(idSesion);
    }
}

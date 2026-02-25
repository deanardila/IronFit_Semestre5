package com.ironfit.ironfit.servicio.seguridad;

import com.ironfit.ironfit.modelo.seguridad.Especialidad;
import com.ironfit.ironfit.repositorio.seguridad.EspecialidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    public List<Especialidad> listarTodos() {
        return especialidadRepository.findAll();
    }

    public Optional<Especialidad> buscarPorId(Long idEspecialidad) {
        return especialidadRepository.findById(idEspecialidad);
    }

    public Optional<Especialidad> buscarPorNombre(String nombre) {
        return especialidadRepository.findByNombre(nombre);
    }

    public Especialidad guardar(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public void eliminar(Long idEspecialidad) {
        especialidadRepository.deleteById(idEspecialidad);
    }

    // Desactivar sin eliminar físicamente
    public void cambiarEstado(Long idEspecialidad, boolean activa) {
        Especialidad esp = especialidadRepository.findById(idEspecialidad)
                .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada: " + idEspecialidad));

        esp.setEstado(activa);
        especialidadRepository.save(esp);
    }
}

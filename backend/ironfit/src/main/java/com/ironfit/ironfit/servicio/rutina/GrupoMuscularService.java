package com.ironfit.ironfit.servicio.rutina;

import com.ironfit.ironfit.modelo.rutina.GrupoMuscular;
import com.ironfit.ironfit.repositorio.rutina.GrupoMuscularRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoMuscularService {

    private final GrupoMuscularRepository grupoMuscularRepository;

    public GrupoMuscularService(GrupoMuscularRepository grupoMuscularRepository) {
        this.grupoMuscularRepository = grupoMuscularRepository;
    }

    public List<GrupoMuscular> listarTodos() {
        return grupoMuscularRepository.findAll();
    }

    public Optional<GrupoMuscular> buscarPorId(Long id) {
        return grupoMuscularRepository.findById(id);
    }

    public GrupoMuscular guardar(GrupoMuscular grupo) {
        return grupoMuscularRepository.save(grupo);
    }

    public void eliminar(Long id) {
        grupoMuscularRepository.deleteById(id);
    }
}


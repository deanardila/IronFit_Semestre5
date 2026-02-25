package com.ironfit.ironfit.servicio.plan;

import com.ironfit.ironfit.modelo.plan.ObjetivoPlan;
import com.ironfit.ironfit.repositorio.plan.ObjetivoPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObjetivoPlanService {

    private final ObjetivoPlanRepository objetivoPlanRepository;

    public ObjetivoPlanService(ObjetivoPlanRepository objetivoPlanRepository) {
        this.objetivoPlanRepository = objetivoPlanRepository;
    }

    public List<ObjetivoPlan> listarTodos() {
        return objetivoPlanRepository.findAll();
    }

    public Optional<ObjetivoPlan> buscarPorId(Long idObjetivo) {
        return objetivoPlanRepository.findById(idObjetivo);
    }

    public Optional<ObjetivoPlan> buscarPorNombre(String nombre) {
        return objetivoPlanRepository.findByNombre(nombre);
    }

    public ObjetivoPlan guardar(ObjetivoPlan objetivoPlan) {
        return objetivoPlanRepository.save(objetivoPlan);
    }

    public void eliminar(Long idObjetivo) {
        objetivoPlanRepository.deleteById(idObjetivo);
    }
}

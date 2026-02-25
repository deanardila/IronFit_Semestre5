package com.ironfit.ironfit.servicio.plan;

import com.ironfit.ironfit.modelo.plan.EstadoPlan;
import com.ironfit.ironfit.repositorio.plan.EstadoPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoPlanService {

    private final EstadoPlanRepository estadoPlanRepository;

    public EstadoPlanService(EstadoPlanRepository estadoPlanRepository) {
        this.estadoPlanRepository = estadoPlanRepository;
    }

    public List<EstadoPlan> listarTodos() {
        return estadoPlanRepository.findAll();
    }

    public Optional<EstadoPlan> buscarPorId(Long idEstadoPlan) {
        return estadoPlanRepository.findById(idEstadoPlan);
    }

    public Optional<EstadoPlan> buscarPorNombre(String nombre) {
        return estadoPlanRepository.findByNombre(nombre);
    }

    public EstadoPlan guardar(EstadoPlan estadoPlan) {
        return estadoPlanRepository.save(estadoPlan);
    }

    public void eliminar(Long idEstadoPlan) {
        estadoPlanRepository.deleteById(idEstadoPlan);
    }
}

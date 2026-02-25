package com.ironfit.ironfit.servicio.rutina;

import com.ironfit.ironfit.dto.RutinaDTO;
import com.ironfit.ironfit.modelo.rutina.Rutina;
import com.ironfit.ironfit.repositorio.rutina.RutinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutinaService {

    private final RutinaRepository rutinaRepository;

    public RutinaService(RutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
    }

    public List<RutinaDTO> obtenerRutinasPorPlan(Long idPlan) {
        List<Rutina> rutinas = rutinaRepository.findByPlanEntrenamiento_IdPlan(idPlan);

        return rutinas.stream()
                .map(r -> new RutinaDTO(
                        r.getIdRutina(),
                        r.getNombre(),
                        r.getDescripcion(),
                        r.getOrden()
                ))
                .collect(Collectors.toList());
    }
}

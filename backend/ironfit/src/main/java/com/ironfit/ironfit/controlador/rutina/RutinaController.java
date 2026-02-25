package com.ironfit.ironfit.controlador.rutina;

import com.ironfit.ironfit.dto.RutinaDTO;
import com.ironfit.ironfit.servicio.rutina.RutinaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutinas")
@CrossOrigin(origins = "*")
public class RutinaController {

    private final RutinaService rutinaService;

    public RutinaController(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    @GetMapping("/plan/{idPlan}")
    public List<RutinaDTO> obtenerPorPlan(@PathVariable Long idPlan) {
        // OJO: aquí devolvemos SOLO DTO, no la entidad Rutina
        return rutinaService.obtenerRutinasPorPlan(idPlan);
    }
}



package com.ironfit.ironfit.controlador.plan;

import com.ironfit.ironfit.modelo.plan.ObjetivoPlan;
import com.ironfit.ironfit.servicio.plan.ObjetivoPlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/objetivos-plan")
@CrossOrigin("*")
public class ObjetivoPlanController {

    private final ObjetivoPlanService objetivoService;

    public ObjetivoPlanController(ObjetivoPlanService objetivoService) {
        this.objetivoService = objetivoService;
    }

    @GetMapping
    public List<ObjetivoPlan> listar() {
        return objetivoService.listarTodos();
    }

    @PostMapping
    public ObjetivoPlan guardar(@RequestBody ObjetivoPlan objetivo) {
        return objetivoService.guardar(objetivo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        objetivoService.eliminar(id);
    }
}


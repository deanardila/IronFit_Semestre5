package com.ironfit.ironfit.controlador.plan;

import com.ironfit.ironfit.modelo.plan.EstadoPlan;
import com.ironfit.ironfit.servicio.plan.EstadoPlanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-plan")
@CrossOrigin("*")
public class EstadoPlanController {

    private final EstadoPlanService estadoService;

    public EstadoPlanController(EstadoPlanService estadoService) {
        this.estadoService = estadoService;
    }

    @GetMapping
    public List<EstadoPlan> listar() {
        return estadoService.listarTodos();
    }

    @PostMapping
    public EstadoPlan guardar(@RequestBody EstadoPlan estado) {
        return estadoService.guardar(estado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        estadoService.eliminar(id);
    }
}


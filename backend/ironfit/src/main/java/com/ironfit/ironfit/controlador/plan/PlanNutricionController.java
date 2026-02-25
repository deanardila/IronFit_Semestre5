package com.ironfit.ironfit.controlador.plan;

import com.ironfit.ironfit.modelo.plan.PlanNutricion;
import com.ironfit.ironfit.servicio.plan.PlanNutricionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes-nutricion")
@CrossOrigin("*")
public class PlanNutricionController {

    private final PlanNutricionService planNutricionService;

    public PlanNutricionController(PlanNutricionService planNutricionService) {
        this.planNutricionService = planNutricionService;
    }

    @GetMapping
    public List<PlanNutricion> listar() {
        return planNutricionService.listarTodos();
    }

    @GetMapping("/{id}")
    public PlanNutricion buscar(@PathVariable Long id) {
        return planNutricionService.buscarPorId(id).orElse(null);
    }

    @GetMapping("/plan/{idPlan}")
    public List<PlanNutricion> listarPorPlan(@PathVariable Long idPlan) {
        return planNutricionService.listarPorPlanEntrenamiento(idPlan);
    }

    @PostMapping
    public PlanNutricion guardar(@RequestBody PlanNutricion plan) {
        return planNutricionService.guardar(plan);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        planNutricionService.eliminar(id);
    }
}


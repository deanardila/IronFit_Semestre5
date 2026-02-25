package com.ironfit.ironfit.controlador.plan;

import com.ironfit.ironfit.dto.PlanResumenDTO;
import com.ironfit.ironfit.dto.RutinaDTO;
import com.ironfit.ironfit.modelo.plan.PlanEntrenamiento;
import com.ironfit.ironfit.servicio.plan.PlanEntrenamientoService;
import com.ironfit.ironfit.servicio.rutina.RutinaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/planes")
@CrossOrigin("*")
public class PlanEntrenamientoController {

    private final PlanEntrenamientoService planService;
    private final RutinaService rutinaService;

    public PlanEntrenamientoController(PlanEntrenamientoService planService,
                                       RutinaService rutinaService) {
        this.planService = planService;
        this.rutinaService = rutinaService;
    }

    @GetMapping
    public List<PlanEntrenamiento> listar() {
        return planService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<PlanEntrenamiento> p = planService.buscarPorId(id);
        return p.isPresent() ? ResponseEntity.ok(p.get())
                             : ResponseEntity.notFound().build();
    }

    @PostMapping
    public PlanEntrenamiento guardar(@RequestBody PlanEntrenamiento plan) {
        return planService.guardar(plan);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        planService.eliminar(id);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<PlanEntrenamiento> listarPorCliente(@PathVariable Long idCliente) {
        return planService.listarPorCliente(idCliente);
    }

    @GetMapping("/entrenador/{idEntrenador}")
    public List<PlanEntrenamiento> listarPorEntrenador(@PathVariable Long idEntrenador) {
        return planService.listarPorEntrenador(idEntrenador);
    }

    @PutMapping("/{idPlan}/objetivo/{idObjetivo}")
    public void cambiarObjetivo(@PathVariable Long idPlan, @PathVariable Long idObjetivo) {
        planService.cambiarObjetivo(idPlan, idObjetivo);
    }

    @PutMapping("/{idPlan}/estado/{idEstado}")
    public void cambiarEstado(@PathVariable Long idPlan, @PathVariable Long idEstado) {
        planService.cambiarEstado(idPlan, idEstado);
    }

    @GetMapping("/resumen")
    public List<PlanResumenDTO> listarPlanesResumen() {
        return planService.listarPlanesResumen();
    }

    @GetMapping("/{idPlan}/rutinas")
    public ResponseEntity<List<RutinaDTO>> obtenerRutinasPorPlan(@PathVariable Long idPlan) {
        List<RutinaDTO> rutinas = rutinaService.obtenerRutinasPorPlan(idPlan);
        return ResponseEntity.ok(rutinas);
    }
}


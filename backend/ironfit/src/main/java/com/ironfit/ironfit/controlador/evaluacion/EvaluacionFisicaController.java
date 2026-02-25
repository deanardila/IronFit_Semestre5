package com.ironfit.ironfit.controlador.evaluacion;

import com.ironfit.ironfit.modelo.evaluacion.EvaluacionFisica;
import com.ironfit.ironfit.servicio.evaluacion.EvaluacionFisicaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones")
@CrossOrigin("*")
public class EvaluacionFisicaController {

    private final EvaluacionFisicaService evaluacionFisicaService;

    public EvaluacionFisicaController(EvaluacionFisicaService evaluacionFisicaService) {
        this.evaluacionFisicaService = evaluacionFisicaService;
    }

    @GetMapping
    public List<EvaluacionFisica> listar() {
        return evaluacionFisicaService.listarTodas();
    }

    @GetMapping("/{id}")
    public EvaluacionFisica buscar(@PathVariable Long id) {
        return evaluacionFisicaService.buscarPorId(id).orElse(null);
    }

    @GetMapping("/cliente/{idCliente}")
    public List<EvaluacionFisica> listarPorCliente(@PathVariable Long idCliente) {
        return evaluacionFisicaService.listarPorCliente(idCliente);
    }

    @GetMapping("/evaluador/{idEvaluador}")
    public List<EvaluacionFisica> listarPorEvaluador(@PathVariable Long idEvaluador) {
        return evaluacionFisicaService.listarPorEvaluador(idEvaluador);
    }

    @PostMapping
    public EvaluacionFisica guardar(@RequestBody EvaluacionFisica evaluacion) {
        return evaluacionFisicaService.guardar(evaluacion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        evaluacionFisicaService.eliminar(id);
    }
}

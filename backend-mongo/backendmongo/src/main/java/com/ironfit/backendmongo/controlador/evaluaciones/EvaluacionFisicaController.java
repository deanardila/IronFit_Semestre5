package com.ironfit.backendmongo.controlador.evaluaciones;

import com.ironfit.backendmongo.dto.evaluaciones.EvaluacionFisicaRequest;
import com.ironfit.backendmongo.dto.evaluaciones.EvaluacionFisicaResponse;
import com.ironfit.backendmongo.servicio.evaluaciones.EvaluacionFisicaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evaluaciones-fisicas")
public class EvaluacionFisicaController {

    private final EvaluacionFisicaService evaluacionFisicaService;

    public EvaluacionFisicaController(EvaluacionFisicaService evaluacionFisicaService) {
        this.evaluacionFisicaService = evaluacionFisicaService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENTRENADOR','CLIENTE')")
    @GetMapping
    public List<EvaluacionFisicaResponse> listarEvaluaciones(Authentication authentication) {
        return evaluacionFisicaService.listarEvaluaciones(authentication);
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/mis-evaluaciones")
    public List<EvaluacionFisicaResponse> listarMisEvaluaciones(Authentication authentication) {
        return evaluacionFisicaService.listarMisEvaluaciones(authentication);
    }

    @PreAuthorize("hasRole('ENTRENADOR')")
    @PostMapping
    public EvaluacionFisicaResponse crearEvaluacion(
            Authentication authentication,
            @RequestBody EvaluacionFisicaRequest request
    ) {
        return evaluacionFisicaService.crearEvaluacion(authentication, request);
    }

    @PreAuthorize("hasRole('ENTRENADOR')")
    @PutMapping("/{id}")
    public EvaluacionFisicaResponse actualizarEvaluacion(
            Authentication authentication,
            @PathVariable String id,
            @RequestBody EvaluacionFisicaRequest request
    ) {
        return evaluacionFisicaService.actualizarEvaluacion(authentication, id, request);
    }

    @PreAuthorize("hasRole('ENTRENADOR')")
    @DeleteMapping("/{id}")
    public void eliminarEvaluacion(
            Authentication authentication,
            @PathVariable String id
    ) {
        evaluacionFisicaService.eliminarEvaluacion(authentication, id);
    }
}
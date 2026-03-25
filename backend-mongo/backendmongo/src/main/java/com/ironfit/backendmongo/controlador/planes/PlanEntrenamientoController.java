package com.ironfit.backendmongo.controlador.planes;

import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoActualizarRequest;
import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoCrearRequest;
import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoResponse;
import com.ironfit.backendmongo.servicio.planes.PlanEntrenamientoService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanEntrenamientoController {

    private final PlanEntrenamientoService planEntrenamientoService;

    public PlanEntrenamientoController(PlanEntrenamientoService planEntrenamientoService) {
        this.planEntrenamientoService = planEntrenamientoService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENTRENADOR')")
    @GetMapping
    public List<PlanEntrenamientoResponse> listarPlanes() {
        return planEntrenamientoService.listarPlanes();
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENTRENADOR')")
    @PostMapping
    public PlanEntrenamientoResponse crearPlan(
            @Valid @RequestBody PlanEntrenamientoCrearRequest request
    ) {
        return planEntrenamientoService.crearPlan(request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENTRENADOR')")
    @PutMapping("/{id}")
    public PlanEntrenamientoResponse actualizarPlan(
            @PathVariable String id,
            @Valid @RequestBody PlanEntrenamientoActualizarRequest request
    ) {
        return planEntrenamientoService.actualizarPlan(id, request);
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENTRENADOR')")
    @PatchMapping("/{id}/estado")
    public void cambiarEstado(
            @PathVariable String id,
            @RequestParam Boolean activo
    ) {
        planEntrenamientoService.cambiarEstado(id, activo);
    }
}
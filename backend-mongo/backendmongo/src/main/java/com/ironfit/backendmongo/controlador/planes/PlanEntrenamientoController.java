package com.ironfit.backendmongo.controlador.planes;

import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoActualizarRequest;
import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoCrearRequest;
import com.ironfit.backendmongo.dto.planes.PlanEntrenamientoResponse;
import com.ironfit.backendmongo.servicio.planes.PlanEntrenamientoService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    public List<PlanEntrenamientoResponse> listarPlanes(Authentication authentication) {
        return planEntrenamientoService.listarPlanes(authentication);
    }

    @PostMapping
    public PlanEntrenamientoResponse crearPlan(
            Authentication authentication,
            @Valid @RequestBody PlanEntrenamientoCrearRequest request
    ) {
        return planEntrenamientoService.crearPlan(authentication, request);
    }

    @PutMapping("/{id}")
    public PlanEntrenamientoResponse actualizarPlan(
            Authentication authentication,
            @PathVariable String id,
            @Valid @RequestBody PlanEntrenamientoActualizarRequest request
    ) {
        return planEntrenamientoService.actualizarPlan(authentication, id, request);
    }

    @PatchMapping("/{id}/estado")
    public PlanEntrenamientoResponse cambiarEstado(
            Authentication authentication,
            @PathVariable String id,
            @RequestParam Boolean activo
    ) {
        return planEntrenamientoService.cambiarEstado(authentication, id, activo);
    }
}
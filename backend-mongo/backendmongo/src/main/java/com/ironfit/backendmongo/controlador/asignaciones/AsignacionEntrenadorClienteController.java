package com.ironfit.backendmongo.controlador.asignaciones;

import com.ironfit.backendmongo.dto.asignaciones.AsignacionEntrenadorClienteRequest;
import com.ironfit.backendmongo.dto.asignaciones.AsignacionEntrenadorClienteResponse;
import com.ironfit.backendmongo.servicio.asignaciones.AsignacionEntrenadorClienteService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionEntrenadorClienteController {

    private final AsignacionEntrenadorClienteService asignacionService;

    public AsignacionEntrenadorClienteController(AsignacionEntrenadorClienteService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AsignacionEntrenadorClienteResponse> listarAsignaciones() {
        return asignacionService.listarAsignaciones();
    }

    @PreAuthorize("hasRole('ENTRENADOR')")
    @GetMapping("/mis-clientes")
    public List<AsignacionEntrenadorClienteResponse> listarMisClientes(Authentication authentication) {
        return asignacionService.listarMisClientes(authentication);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public AsignacionEntrenadorClienteResponse crearAsignacion(
            @RequestBody AsignacionEntrenadorClienteRequest request
    ) {
        return asignacionService.crearAsignacion(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/estado")
    public AsignacionEntrenadorClienteResponse cambiarEstado(
            @PathVariable String id,
            @RequestParam Boolean activo
    ) {
        return asignacionService.cambiarEstado(id, activo);
    }
}
package com.ironfit.backendmongo.controlador.ejercicios;

import com.ironfit.backendmongo.dto.ejercicios.EjercicioActualizarRequest;
import com.ironfit.backendmongo.dto.ejercicios.EjercicioCrearRequest;
import com.ironfit.backendmongo.dto.ejercicios.EjercicioResponse;
import com.ironfit.backendmongo.servicio.ejercicios.EjercicioService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioController {

    private final EjercicioService ejercicioService;

    public EjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','ENTRENADOR','CLIENTE')")
    @GetMapping
    public List<EjercicioResponse> listarEjercicios() {
        return ejercicioService.listarEjercicios();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public EjercicioResponse crearEjercicio(@Valid @RequestBody EjercicioCrearRequest request) {
        return ejercicioService.crearEjercicio(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public EjercicioResponse actualizarEjercicio(
            @PathVariable String id,
            @Valid @RequestBody EjercicioActualizarRequest request
    ) {
        return ejercicioService.actualizarEjercicio(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void eliminarEjercicio(@PathVariable String id) {
        ejercicioService.eliminarEjercicio(id);
    }
}
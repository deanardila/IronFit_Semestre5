package com.ironfit.ironfit.controlador.rutina;

import com.ironfit.ironfit.modelo.rutina.GrupoMuscular;
import com.ironfit.ironfit.servicio.rutina.GrupoMuscularService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos-musculares")
@CrossOrigin("*")
public class GrupoMuscularController {

    private final GrupoMuscularService grupoMuscularService;

    public GrupoMuscularController(GrupoMuscularService grupoMuscularService) {
        this.grupoMuscularService = grupoMuscularService;
    }

    @GetMapping
    public List<GrupoMuscular> listar() {
        return grupoMuscularService.listarTodos();
    }

    @PostMapping
    public GrupoMuscular guardar(@RequestBody GrupoMuscular grupo) {
        return grupoMuscularService.guardar(grupo);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        grupoMuscularService.eliminar(id);
    }
}

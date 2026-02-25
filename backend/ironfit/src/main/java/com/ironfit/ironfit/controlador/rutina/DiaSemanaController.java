package com.ironfit.ironfit.controlador.rutina;

import com.ironfit.ironfit.modelo.rutina.DiaSemana;
import com.ironfit.ironfit.servicio.rutina.DiaSemanaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dias-semana")
@CrossOrigin("*")
public class DiaSemanaController {

    private final DiaSemanaService diaSemanaService;

    public DiaSemanaController(DiaSemanaService diaSemanaService) {
        this.diaSemanaService = diaSemanaService;
    }

    @GetMapping
    public List<DiaSemana> listar() {
        return diaSemanaService.listarTodos();
    }

    @GetMapping("/{id}")
    public DiaSemana buscar(@PathVariable Short id) {
        return diaSemanaService.buscarPorId(id).orElse(null);
    }

    @PostMapping
    public DiaSemana guardar(@RequestBody DiaSemana diaSemana) {
        return diaSemanaService.guardar(diaSemana);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Short id) {
        diaSemanaService.eliminar(id);
    }
}

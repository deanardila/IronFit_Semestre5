package com.ironfit.ironfit.controlador.asistencia;

import com.ironfit.ironfit.modelo.asistencia.AsistenciaEjercicio;
import com.ironfit.ironfit.servicio.asistencia.AsistenciaEjercicioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asistencia-ejercicios")
@CrossOrigin("*")
public class AsistenciaEjercicioController {

    private final AsistenciaEjercicioService asistenciaEjercicioService;

    public AsistenciaEjercicioController(AsistenciaEjercicioService asistenciaEjercicioService) {
        this.asistenciaEjercicioService = asistenciaEjercicioService;
    }

    @GetMapping
    public List<AsistenciaEjercicio> listar() {
        return asistenciaEjercicioService.listarTodos();
    }

    @GetMapping("/asistencia/{idAsistencia}")
    public List<AsistenciaEjercicio> listarPorAsistencia(@PathVariable Long idAsistencia) {
        return asistenciaEjercicioService.listarPorAsistencia(idAsistencia);
    }

    @PostMapping
    public AsistenciaEjercicio guardar(@RequestBody AsistenciaEjercicio registro) {
        return asistenciaEjercicioService.guardar(registro);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        asistenciaEjercicioService.eliminar(id);
    }
}

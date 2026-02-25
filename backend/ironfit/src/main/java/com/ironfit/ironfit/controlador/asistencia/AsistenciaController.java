package com.ironfit.ironfit.controlador.asistencia;

import com.ironfit.ironfit.dto.AsistenciaSesionDTO;
import com.ironfit.ironfit.dto.ResumenAsistenciaPlanDTO;
import com.ironfit.ironfit.modelo.asistencia.Asistencia;
import com.ironfit.ironfit.servicio.asistencia.AsistenciaService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@CrossOrigin("*")
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    public AsistenciaController(AsistenciaService asistenciaService) {
        this.asistenciaService = asistenciaService;
    }

    @GetMapping
    public List<Asistencia> listar() {
        return asistenciaService.listarTodas();
    }

    @GetMapping("/{id}")
    public Asistencia buscar(@PathVariable Long id) {
        return asistenciaService.buscarPorId(id).orElse(null);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Asistencia> listarPorUsuario(@PathVariable Long idUsuario) {
        return asistenciaService.listarPorUsuario(idUsuario);
    }

    @GetMapping("/sesion/{idSesion}")
    public List<Asistencia> listarPorSesion(@PathVariable Long idSesion) {
        return asistenciaService.listarPorSesion(idSesion);
    }

    @GetMapping("/fecha/{fecha}")
    public List<Asistencia> listarPorFecha(@PathVariable String fecha) {
        LocalDate f = LocalDate.parse(fecha); // formato: 2025-11-16
        return asistenciaService.listarPorFecha(f);
    }

    @PostMapping
    public Asistencia guardar(@RequestBody Asistencia asistencia) {
        return asistenciaService.guardar(asistencia);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        asistenciaService.eliminar(id);
    }

    @GetMapping("/entrenador/{idEntrenador}/resumen")
    public List<ResumenAsistenciaPlanDTO> obtenerResumenPorEntrenador(
            @PathVariable Long idEntrenador) {
        return asistenciaService.obtenerResumenPorEntrenador(idEntrenador);
    }

    @GetMapping("/plan/{idPlan}/cliente/{idCliente}")
    public List<AsistenciaSesionDTO> obtenerAsistenciaPorPlanYCliente(
            @PathVariable Long idPlan,
            @PathVariable Long idCliente) {
        return asistenciaService.obtenerAsistenciaPorPlanYCliente(idPlan, idCliente);
    }
}

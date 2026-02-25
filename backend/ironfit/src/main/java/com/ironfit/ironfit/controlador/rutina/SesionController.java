package com.ironfit.ironfit.controlador.rutina;

import com.ironfit.ironfit.modelo.rutina.Sesion;
import com.ironfit.ironfit.servicio.rutina.SesionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sesiones")
@CrossOrigin("*")
public class SesionController {

    private final SesionService sesionService;

    public SesionController(SesionService sesionService) {
        this.sesionService = sesionService;
    }

    @GetMapping
    public List<Sesion> listar() {
        return sesionService.listarTodas();
    }

    @GetMapping("/{id}")
    public Sesion buscar(@PathVariable Long id) {
        return sesionService.buscarPorId(id).orElse(null);
    }

    @GetMapping("/rutina/{idRutina}")
    public List<Sesion> listarPorRutina(@PathVariable Long idRutina) {
        return sesionService.listarPorRutina(idRutina);
    }

    @PostMapping
    public Sesion guardar(@RequestBody Sesion sesion) {
        return sesionService.guardar(sesion);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        sesionService.eliminar(id);
    }
}

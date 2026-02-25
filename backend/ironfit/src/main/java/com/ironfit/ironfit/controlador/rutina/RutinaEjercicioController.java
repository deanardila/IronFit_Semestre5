package com.ironfit.ironfit.controlador.rutina;

import com.ironfit.ironfit.modelo.rutina.RutinaEjercicio;
import com.ironfit.ironfit.modelo.rutina.RutinaEjercicioId;
import com.ironfit.ironfit.servicio.rutina.RutinaEjercicioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rutina-ejercicios")
@CrossOrigin("*")
public class RutinaEjercicioController {

    private final RutinaEjercicioService rutinaEjercicioService;

    public RutinaEjercicioController(RutinaEjercicioService rutinaEjercicioService) {
        this.rutinaEjercicioService = rutinaEjercicioService;
    }

    @GetMapping("/rutina/{idRutina}")
    public List<RutinaEjercicio> listarPorRutina(@PathVariable Long idRutina) {
        return rutinaEjercicioService.listarPorRutina(idRutina);
    }

    @PostMapping
    public RutinaEjercicio guardar(@RequestBody RutinaEjercicio rutinaEjercicio) {
        return rutinaEjercicioService.guardar(rutinaEjercicio);
    }

    @DeleteMapping("/rutina/{idRutina}/ejercicio/{idEjercicio}/orden/{orden}")
    public void eliminar(@PathVariable Long idRutina,
                            @PathVariable Long idEjercicio,
                            @PathVariable Short orden) {

        RutinaEjercicioId id = new RutinaEjercicioId();
        id.setIdRutina(idRutina);
        id.setIdEjercicio(idEjercicio);
        id.setOrden(orden);

        rutinaEjercicioService.eliminar(id);
    }
}


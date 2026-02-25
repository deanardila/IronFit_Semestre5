package com.ironfit.ironfit.controlador.seguridad;

import com.ironfit.ironfit.modelo.seguridad.Especialidad;
import com.ironfit.ironfit.servicio.seguridad.EspecialidadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@CrossOrigin(origins = "*")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public List<Especialidad> listar() {
        return especialidadService.listarTodos();
    }

    @PostMapping
    public Especialidad guardar(@RequestBody Especialidad esp) {
        return especialidadService.guardar(esp);
    }

    @PutMapping("/{id}/estado")
    public void cambiarEstado(@PathVariable Long id,
                                @RequestParam boolean activa) {
        especialidadService.cambiarEstado(id, activa);
    }
}

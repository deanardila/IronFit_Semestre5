package com.ironfit.ironfit.controlador.seguridad;

import com.ironfit.ironfit.modelo.seguridad.Permiso;
import com.ironfit.ironfit.servicio.seguridad.PermisoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@CrossOrigin(origins = "*")
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    @GetMapping
    public List<Permiso> listar() {
        return permisoService.listarTodos();
    }

    @PostMapping
    public Permiso guardar(@RequestBody Permiso permiso) {
        return permisoService.guardar(permiso);
    }

    @PostMapping("/{idRol}/asignar/{idPermiso}")
    public ResponseEntity<?> asignar(@PathVariable Long idRol,
                                        @PathVariable Long idPermiso) {
        permisoService.asignarPermisoARol(idRol, idPermiso);
        return ResponseEntity.ok("Permiso asignado al rol");
    }

    @DeleteMapping("/{idRol}/quitar/{idPermiso}")
    public ResponseEntity<?> quitar(@PathVariable Long idRol,
                                    @PathVariable Long idPermiso) {
        permisoService.quitarPermisoARol(idRol, idPermiso);
        return ResponseEntity.ok("Permiso removido del rol");
    }
}


package com.ironfit.ironfit.controlador.seguridad;

import com.ironfit.ironfit.modelo.seguridad.Rol;
import com.ironfit.ironfit.servicio.seguridad.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<Rol> listar() {
        return rolService.listarTodos();
    }

    @PostMapping
    public Rol guardar(@RequestBody Rol rol) {
        return rolService.guardar(rol);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<Rol> r = rolService.buscarPorId(id);
        return r.isPresent() ? ResponseEntity.ok(r.get())
                                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
    }
}


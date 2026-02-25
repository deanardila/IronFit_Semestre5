package com.ironfit.ironfit.controlador.seguridad;

import com.ironfit.ironfit.dto.UsuarioCrearDTO;
import com.ironfit.ironfit.dto.UsuarioResumenDTO;
import com.ironfit.ironfit.modelo.seguridad.Usuario;
import com.ironfit.ironfit.servicio.seguridad.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        Optional<Usuario> u = usuarioService.buscarPorId(id);
        return u.isPresent() ? ResponseEntity.ok(u.get())
                                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Usuario guardar(@RequestBody Usuario usuario) {
        return usuarioService.guardar(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }

    // -------- ROLES ---------

    @PostMapping("/{idUsuario}/rol/{idRol}")
    public ResponseEntity<?> asignarRol(@PathVariable Long idUsuario,
                                        @PathVariable Long idRol) {
        usuarioService.asignarRolAUsuario(idUsuario, idRol);
        return ResponseEntity.ok("Rol asignado correctamente");
    }

    @DeleteMapping("/{idUsuario}/rol/{idRol}")
    public ResponseEntity<?> quitarRol(@PathVariable Long idUsuario,
                                        @PathVariable Long idRol) {
        usuarioService.quitarRolDeUsuario(idUsuario, idRol);
        return ResponseEntity.ok("Rol removido");
    }

    // -------- ESPECIALIDADES ---------

    @PostMapping("/{idUsuario}/especialidad/{idEspecialidad}")
    public ResponseEntity<?> asignarEspecialidad(@PathVariable Long idUsuario,
                                                    @PathVariable Long idEspecialidad) {
        usuarioService.asignarEspecialidadAUsuario(idUsuario, idEspecialidad);
        return ResponseEntity.ok("Especialidad asignada");
    }

    @DeleteMapping("/{idUsuario}/especialidad/{idEspecialidad}")
    public ResponseEntity<?> quitarEspecialidad(@PathVariable Long idUsuario,
                                                @PathVariable Long idEspecialidad) {
        usuarioService.quitarEspecialidadDeUsuario(idUsuario, idEspecialidad);
        return ResponseEntity.ok("Especialidad removida");
    }

    // -------- RESUMEN ---------
    @GetMapping("/resumen")
    public List<UsuarioResumenDTO> listarUsuariosResumen() {
        return usuarioService.obtenerResumenUsuarios();
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crear(@RequestBody UsuarioCrearDTO dto) {
        try {
            Usuario guardado = usuarioService.crearUsuarioConRol(dto);
            return ResponseEntity.ok(guardado); // o mapear a UsuarioResumenDTO
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }


}



package com.ironfit.ironfit.controlador.rutina;

import com.ironfit.ironfit.modelo.rutina.Categoria;
import com.ironfit.ironfit.modelo.rutina.Ejercicio;
import com.ironfit.ironfit.modelo.rutina.GrupoMuscular;
import com.ironfit.ironfit.servicio.rutina.CategoriaService;
import com.ironfit.ironfit.servicio.rutina.EjercicioService;
import com.ironfit.ironfit.servicio.rutina.GrupoMuscularService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejercicios")
@CrossOrigin("*")
public class EjercicioController {

    private final EjercicioService ejercicioService;
    private final CategoriaService categoriaService;
    private final GrupoMuscularService grupoMuscularService;

    public EjercicioController(EjercicioService ejercicioService,
                                CategoriaService categoriaService,
                                GrupoMuscularService grupoMuscularService) {
        this.ejercicioService = ejercicioService;
        this.categoriaService = categoriaService;
        this.grupoMuscularService = grupoMuscularService;
    }

    // LISTAR TODOS
    @GetMapping
    public List<Ejercicio> listar() {
        return ejercicioService.listarTodos();
    }

    // CREAR / EDITAR (por ahora crear)
    @PostMapping
    public Ejercicio guardar(@RequestBody Ejercicio ejercicio) {
        return ejercicioService.guardar(ejercicio);
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        return ejercicioService.buscar(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        ejercicioService.eliminar(id);
    }

    // TOTAL DE EJERCICIOS (para dashboard admin)
    @GetMapping("/total")
    public long getTotalEjercicios() {
        return ejercicioService.contarEjercicios();
    }

    @GetMapping("/categorias")
    public List<Categoria> listarCategorias() {
        return categoriaService.listarTodos();
    }

    @GetMapping("/grupos-musculares")
    public List<GrupoMuscular> listarGrupos() {
        return grupoMuscularService.listarTodos();
    }
}


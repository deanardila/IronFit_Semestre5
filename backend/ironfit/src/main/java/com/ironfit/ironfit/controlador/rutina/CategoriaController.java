package com.ironfit.ironfit.controlador.rutina;

import com.ironfit.ironfit.modelo.rutina.Categoria;
import com.ironfit.ironfit.servicio.rutina.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin("*")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listarTodos();
    }

    @PostMapping
    public Categoria guardar(@RequestBody Categoria categoria) {
        return categoriaService.guardar(categoria);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
    }
}

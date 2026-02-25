package com.ironfit.ironfit.servicio.rutina;

import com.ironfit.ironfit.modelo.rutina.Ejercicio;
import com.ironfit.ironfit.repositorio.rutina.EjercicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;

    public EjercicioService(EjercicioRepository ejercicioRepository) {
        this.ejercicioRepository = ejercicioRepository;
    }

    // LISTAR TODOS LOS EJERCICIOS (para catálogo)
    public List<Ejercicio> listarTodos() {
        return ejercicioRepository.findAll();
    }

    // BUSCAR POR ID
    public Optional<Ejercicio> buscar(Long id) {
        return ejercicioRepository.findById(id);
    }

    // GUARDAR / ACTUALIZAR
    public Ejercicio guardar(Ejercicio ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }

    // ELIMINAR
    public void eliminar(Long id) {
        ejercicioRepository.deleteById(id);
    }

    // CONTAR (para tarjeta del dashboard)
    public long contarEjercicios() {
        return ejercicioRepository.count();
    }

}



package com.ironfit.backendmongo.servicio.ejercicios;

import com.ironfit.backendmongo.dto.ejercicios.EjercicioActualizarRequest;
import com.ironfit.backendmongo.dto.ejercicios.EjercicioCrearRequest;
import com.ironfit.backendmongo.dto.ejercicios.EjercicioResponse;
import com.ironfit.backendmongo.modelo.ejercicios.Ejercicio;
import com.ironfit.backendmongo.repositorio.ejercicios.EjercicioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;

    public EjercicioService(EjercicioRepository ejercicioRepository) {
        this.ejercicioRepository = ejercicioRepository;
    }

    public List<EjercicioResponse> listarEjercicios() {
        return ejercicioRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public EjercicioResponse crearEjercicio(EjercicioCrearRequest request) {
        if (ejercicioRepository.existsByNombreIgnoreCase(request.getNombre().trim())) {
            throw new RuntimeException("Ya existe un ejercicio con ese nombre");
        }

        LocalDateTime ahora = LocalDateTime.now();

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombre(request.getNombre().trim());
        ejercicio.setDescripcion(request.getDescripcion().trim());
        ejercicio.setCategoria(request.getCategoria().trim());
        ejercicio.setGrupoMuscular(request.getGrupoMuscular().trim());
        ejercicio.setSeriesSugeridas(request.getSeriesSugeridas());
        ejercicio.setRepeticionesSugeridas(request.getRepeticionesSugeridas());
        ejercicio.setTipoEquipo(request.getTipoEquipo() != null ? request.getTipoEquipo().trim() : null);
        ejercicio.setActivo(true);
        ejercicio.setFechaCreacion(ahora);
        ejercicio.setFechaActualizacion(ahora);

        Ejercicio guardado = ejercicioRepository.save(ejercicio);
        return convertirAResponse(guardado);
    }

    public EjercicioResponse actualizarEjercicio(String id, EjercicioActualizarRequest request) {
        Ejercicio ejercicio = ejercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        String nombreNuevo = request.getNombre().trim();

        ejercicioRepository.findByNombreIgnoreCase(nombreNuevo).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new RuntimeException("Ya existe otro ejercicio con ese nombre");
            }
        });

        ejercicio.setNombre(nombreNuevo);
        ejercicio.setDescripcion(request.getDescripcion().trim());
        ejercicio.setCategoria(request.getCategoria().trim());
        ejercicio.setGrupoMuscular(request.getGrupoMuscular().trim());
        ejercicio.setSeriesSugeridas(request.getSeriesSugeridas());
        ejercicio.setRepeticionesSugeridas(request.getRepeticionesSugeridas());
        ejercicio.setTipoEquipo(request.getTipoEquipo() != null ? request.getTipoEquipo().trim() : null);
        ejercicio.setFechaActualizacion(LocalDateTime.now());

        Ejercicio actualizado = ejercicioRepository.save(ejercicio);
        return convertirAResponse(actualizado);
    }

    public void eliminarEjercicio(String id) {
        Ejercicio ejercicio = ejercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        ejercicioRepository.delete(ejercicio);
    }

    private EjercicioResponse convertirAResponse(Ejercicio ejercicio) {
        EjercicioResponse response = new EjercicioResponse();
        response.setId(ejercicio.getId());
        response.setNombre(ejercicio.getNombre());
        response.setDescripcion(ejercicio.getDescripcion());
        response.setCategoria(ejercicio.getCategoria());
        response.setGrupoMuscular(ejercicio.getGrupoMuscular());
        response.setSeriesSugeridas(ejercicio.getSeriesSugeridas());
        response.setRepeticionesSugeridas(ejercicio.getRepeticionesSugeridas());
        response.setTipoEquipo(ejercicio.getTipoEquipo());
        return response;
    }
}
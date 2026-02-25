package com.ironfit.ironfit.servicio.rutina;

import com.ironfit.ironfit.modelo.rutina.DiaSemana;
import com.ironfit.ironfit.repositorio.rutina.DiaSemanaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaSemanaService {

    private final DiaSemanaRepository diaSemanaRepository;

    public DiaSemanaService(DiaSemanaRepository diaSemanaRepository) {
        this.diaSemanaRepository = diaSemanaRepository;
    }

    public List<DiaSemana> listarTodos() {
        return diaSemanaRepository.findAll();
    }

    public Optional<DiaSemana> buscarPorId(Short idDia) {
        return diaSemanaRepository.findById(idDia);
    }

    public DiaSemana guardar(DiaSemana diaSemana) {
        return diaSemanaRepository.save(diaSemana);
    }

    public void eliminar(Short idDia) {
        diaSemanaRepository.deleteById(idDia);
    }
}

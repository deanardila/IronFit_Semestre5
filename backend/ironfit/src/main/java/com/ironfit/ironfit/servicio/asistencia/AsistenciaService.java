package com.ironfit.ironfit.servicio.asistencia;

import com.ironfit.ironfit.dto.AsistenciaSesionDTO;
import com.ironfit.ironfit.dto.ResumenAsistenciaPlanDTO;
import com.ironfit.ironfit.modelo.asistencia.Asistencia;
import com.ironfit.ironfit.repositorio.asistencia.AsistenciaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;

    public AsistenciaService(AsistenciaRepository asistenciaRepository) {
        this.asistenciaRepository = asistenciaRepository;
    }

    // -------------------- CRUD BÁSICO --------------------

    public List<Asistencia> listarTodas() {
        return asistenciaRepository.findAll();
    }

    public Optional<Asistencia> buscarPorId(Long idAsistencia) {
        return asistenciaRepository.findById(idAsistencia);
    }

    public Asistencia guardar(Asistencia asistencia) {
        return asistenciaRepository.save(asistencia);
    }

    public void eliminar(Long idAsistencia) {
        asistenciaRepository.deleteById(idAsistencia);
    }

    // -------------------- CONSULTAS --------------------

    public List<Asistencia> listarPorUsuario(Long idUsuario) {
        return asistenciaRepository.findByUsuario_IdUsuario(idUsuario);
    }

    public List<Asistencia> listarPorSesion(Long idSesion) {
        return asistenciaRepository.findBySesion_IdSesion(idSesion);
    }

    public List<Asistencia> listarPorFecha(LocalDate fecha) {
        return asistenciaRepository.findByFecha(fecha);
    }

    public long contarAuditoriasMesActual() {
    LocalDate hoy = LocalDate.now();
    LocalDate inicioMes = hoy.withDayOfMonth(1);
    LocalDate finMes = hoy.withDayOfMonth(hoy.lengthOfMonth());

    return asistenciaRepository.countByFechaBetween(inicioMes, finMes);
    }

    public List<ResumenAsistenciaPlanDTO> obtenerResumenPorEntrenador(Long idEntrenador) {
        return asistenciaRepository.obtenerResumenPorEntrenador(idEntrenador);
    }

    public List<AsistenciaSesionDTO> obtenerAsistenciaPorPlanYCliente(Long idPlan, Long idCliente) {
        return asistenciaRepository.obtenerAsistenciaPorPlanYCliente(idPlan, idCliente);
    }
}

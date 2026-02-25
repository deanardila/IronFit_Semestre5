package com.ironfit.ironfit.repositorio.asistencia;

import com.ironfit.ironfit.dto.AsistenciaSesionDTO;
import com.ironfit.ironfit.dto.ResumenAsistenciaPlanDTO;
import com.ironfit.ironfit.modelo.asistencia.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    // ==============================
    // MÉTODOS QUE YA TENÍAS
    // ==============================

        List<Asistencia> findByUsuario_IdUsuario(Long idUsuario);

        List<Asistencia> findBySesion_IdSesion(Long idSesion);

        List<Asistencia> findByFecha(LocalDate fecha);

        @Query("""
                SELECT COUNT(a)
                FROM Asistencia a
                WHERE a.fecha BETWEEN :inicio AND :fin
                """)
        long countByFechaBetween(@Param("inicio") LocalDate inicio,
                                @Param("fin") LocalDate fin);


        @Query("""
        SELECT new com.ironfit.ironfit.dto.ResumenAsistenciaPlanDTO(
                p.idPlan,
                c.idUsuario,
                CONCAT(c.nombres, ' ', c.apellidos),
                p.nombre,
                COUNT(DISTINCT s.idSesion),
                COUNT(DISTINCT a.idAsistencia),
                SUM(CASE WHEN a.cumplioRutina = true THEN 1 ELSE 0 END)
        )
        FROM Asistencia a
        JOIN a.usuario c
        JOIN a.sesion s
        JOIN s.rutina r
        JOIN r.planEntrenamiento p
        JOIN p.usuarioEntrenador e
        WHERE e.idUsuario = :idEntrenador
        GROUP BY p.idPlan, c.idUsuario, c.nombres, c.apellidos, p.nombre
        """)
List<ResumenAsistenciaPlanDTO> obtenerResumenPorEntrenador(
        @Param("idEntrenador") Long idEntrenador
);



        @Query("""
                SELECT new com.ironfit.ironfit.dto.AsistenciaSesionDTO(
                        s.idSesion,
                        COALESCE(a.fecha, CURRENT_DATE),
                        s.diaSemana.nombre,
                        CASE
                        WHEN a.idAsistencia IS NULL
                                THEN com.ironfit.ironfit.modelo.asistencia.EstadoAsistencia.NO_ASISTIO
                        WHEN a.cumplioRutina = true
                                THEN com.ironfit.ironfit.modelo.asistencia.EstadoAsistencia.ASISTIO_HIZO_RUTINA
                        ELSE com.ironfit.ironfit.modelo.asistencia.EstadoAsistencia.ASISTIO_NO_HIZO_RUTINA
                        END,
                        a.cumplioRutina,
                        NULL,
                        NULL,
                        NULL
                )
                FROM Sesion s
                JOIN s.rutina r
                JOIN r.planEntrenamiento p
                JOIN p.usuarioCliente c
                LEFT JOIN Asistencia a
                ON a.sesion = s
                AND a.usuario = c
                WHERE p.idPlan     = :idPlan
                AND c.idUsuario  = :idCliente
                ORDER BY COALESCE(a.fecha, CURRENT_DATE) ASC
                """)
        List<AsistenciaSesionDTO> obtenerAsistenciaPorPlanYCliente(
                @Param("idPlan") Long idPlan,
                @Param("idCliente") Long idCliente
        );
}

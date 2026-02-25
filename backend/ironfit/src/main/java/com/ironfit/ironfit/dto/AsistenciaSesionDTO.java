package com.ironfit.ironfit.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ironfit.ironfit.modelo.asistencia.EstadoAsistencia;

public class AsistenciaSesionDTO {

    private Long idSesion;
    private LocalDate fecha;
    private String diaSemana;

    private EstadoAsistencia estadoAsistencia;
    private Boolean cumplioRutina;

    private Integer ejerciciosTotales;
    private Integer ejerciciosCumplidos;
    private BigDecimal porcentajeCumplimiento;

    public AsistenciaSesionDTO(Long idSesion,
                                LocalDate fecha,
                                String diaSemana,
                                EstadoAsistencia estadoAsistencia,
                                Boolean cumplioRutina,
                                Integer ejerciciosTotales,
                                Integer ejerciciosCumplidos,
                                BigDecimal porcentajeCumplimiento) {
        this.idSesion = idSesion;
        this.fecha = fecha;
        this.diaSemana = diaSemana;
        this.estadoAsistencia = estadoAsistencia;
        this.cumplioRutina = cumplioRutina;
        this.ejerciciosTotales = ejerciciosTotales;
        this.ejerciciosCumplidos = ejerciciosCumplidos;
        this.porcentajeCumplimiento = porcentajeCumplimiento;
    }

    public AsistenciaSesionDTO() {}

    public Long getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public EstadoAsistencia getEstadoAsistencia() {
        return estadoAsistencia;
    }

    public void setEstadoAsistencia(EstadoAsistencia estadoAsistencia) {
        this.estadoAsistencia = estadoAsistencia;
    }

    public Boolean getCumplioRutina() {
        return cumplioRutina;
    }

    public void setCumplioRutina(Boolean cumplioRutina) {
        this.cumplioRutina = cumplioRutina;
    }

    public Integer getEjerciciosTotales() {
        return ejerciciosTotales;
    }

    public void setEjerciciosTotales(Integer ejerciciosTotales) {
        this.ejerciciosTotales = ejerciciosTotales;
    }

    public Integer getEjerciciosCumplidos() {
        return ejerciciosCumplidos;
    }

    public void setEjerciciosCumplidos(Integer ejerciciosCumplidos) {
        this.ejerciciosCumplidos = ejerciciosCumplidos;
    }

    public BigDecimal getPorcentajeCumplimiento() {
        return porcentajeCumplimiento;
    }

    public void setPorcentajeCumplimiento(BigDecimal porcentajeCumplimiento) {
        this.porcentajeCumplimiento = porcentajeCumplimiento;
    }

    
}

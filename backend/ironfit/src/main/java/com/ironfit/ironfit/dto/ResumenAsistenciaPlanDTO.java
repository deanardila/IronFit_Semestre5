package com.ironfit.ironfit.dto;

import java.math.BigDecimal;

public class ResumenAsistenciaPlanDTO {

    private Long idPlan;
    private Long idCliente;
    private String nombreCliente;
    private String nombrePlan;

    private Long totalSesionesPlan;      // total sesiones planificadas
    private Long sesionesAsistidas;      // con registro en Asistencia
    private BigDecimal porcentajeAsistencia;

    private Long sesionesCompletas;      // cumplioRutina = true
    private BigDecimal porcentajeCompletas;

    public ResumenAsistenciaPlanDTO(Long idPlan,
                                    Long idCliente,
                                    String nombreCliente,
                                    String nombrePlan,
                                    Long totalSesionesPlan,
                                    Long sesionesAsistidas,
                                    Long sesionesCompletas) {
        this.idPlan = idPlan;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.nombrePlan = nombrePlan;
        this.totalSesionesPlan = totalSesionesPlan;
        this.sesionesAsistidas = sesionesAsistidas;
        this.sesionesCompletas = sesionesCompletas;

        if (totalSesionesPlan != null && totalSesionesPlan > 0) {
            this.porcentajeAsistencia = BigDecimal
                    .valueOf(sesionesAsistidas * 100.0 / totalSesionesPlan);
            this.porcentajeCompletas = BigDecimal
                    .valueOf(sesionesCompletas * 100.0 / totalSesionesPlan);
        } else {
            this.porcentajeAsistencia = BigDecimal.ZERO;
            this.porcentajeCompletas = BigDecimal.ZERO;
        }
    }

    public ResumenAsistenciaPlanDTO() {
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public Long getTotalSesionesPlan() {
        return totalSesionesPlan;
    }

    public void setTotalSesionesPlan(Long totalSesionesPlan) {
        this.totalSesionesPlan = totalSesionesPlan;
    }

    public Long getSesionesAsistidas() {
        return sesionesAsistidas;
    }

    public void setSesionesAsistidas(Long sesionesAsistidas) {
        this.sesionesAsistidas = sesionesAsistidas;
    }

    public BigDecimal getPorcentajeAsistencia() {
        return porcentajeAsistencia;
    }

    public void setPorcentajeAsistencia(BigDecimal porcentajeAsistencia) {
        this.porcentajeAsistencia = porcentajeAsistencia;
    }

    public Long getSesionesCompletas() {
        return sesionesCompletas;
    }

    public void setSesionesCompletas(Long sesionesCompletas) {
        this.sesionesCompletas = sesionesCompletas;
    }

    public BigDecimal getPorcentajeCompletas() {
        return porcentajeCompletas;
    }

    public void setPorcentajeCompletas(BigDecimal porcentajeCompletas) {
        this.porcentajeCompletas = porcentajeCompletas;
    }

    
}

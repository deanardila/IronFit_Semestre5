package com.ironfit.ironfit.dto;

public class PlanResumenDTO {

    private Long idPlan;
    private String nombrePlan;
    private String estado;          // ACTIVO / FINALIZADO / etc
    private String fechaInicio;
    private String fechaFin;

    private Long idCliente;
    private String nombreCliente;

    private Long idEntrenador;
    private String nombreEntrenador;

    public PlanResumenDTO(Long idPlan, String nombrePlan, String estado,
                            String fechaInicio, String fechaFin,
                            Long idCliente, String nombreCliente,
                            Long idEntrenador, String nombreEntrenador) {
        this.idPlan = idPlan;
        this.nombrePlan = nombrePlan;
        this.estado = estado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.idEntrenador = idEntrenador;
        this.nombreEntrenador = nombreEntrenador;
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombrePlan() {
        return nombrePlan;
    }

    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
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

    public Long getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(Long idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getNombreEntrenador() {
        return nombreEntrenador;
    }

    public void setNombreEntrenador(String nombreEntrenador) {
        this.nombreEntrenador = nombreEntrenador;
    }

    


}

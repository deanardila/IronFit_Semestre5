package com.ironfit.ironfit.dto;

public class DashboardAdminResumen {

    private long clientesActivos;
    private long entrenadoresActivos;
    private long ejerciciosRegistrados;
    private long asistenciasMes;        // total registros de asistencia del mes
    private double cumplimientoPromedio; // % sesiones completadas del mes

    public DashboardAdminResumen() {
    }

    public DashboardAdminResumen(long clientesActivos,
                                    long entrenadoresActivos,
                                    long ejerciciosRegistrados,
                                    long asistenciasMes,
                                    double cumplimientoPromedio) {
        this.clientesActivos = clientesActivos;
        this.entrenadoresActivos = entrenadoresActivos;
        this.ejerciciosRegistrados = ejerciciosRegistrados;
        this.asistenciasMes = asistenciasMes;
        this.cumplimientoPromedio = cumplimientoPromedio;
    }

    public long getClientesActivos() { return clientesActivos; }
    public void setClientesActivos(long clientesActivos) { this.clientesActivos = clientesActivos; }

    public long getEntrenadoresActivos() { return entrenadoresActivos; }
    public void setEntrenadoresActivos(long entrenadoresActivos) { this.entrenadoresActivos = entrenadoresActivos; }

    public long getEjerciciosRegistrados() { return ejerciciosRegistrados; }
    public void setEjerciciosRegistrados(long ejerciciosRegistrados) { this.ejerciciosRegistrados = ejerciciosRegistrados; }

    public long getAsistenciasMes() { return asistenciasMes; }
    public void setAsistenciasMes(long asistenciasMes) { this.asistenciasMes = asistenciasMes; }

    public double getCumplimientoPromedio() { return cumplimientoPromedio; }
    public void setCumplimientoPromedio(double cumplimientoPromedio) { this.cumplimientoPromedio = cumplimientoPromedio; }
}

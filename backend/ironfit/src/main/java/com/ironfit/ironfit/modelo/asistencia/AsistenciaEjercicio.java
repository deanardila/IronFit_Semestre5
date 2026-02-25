package com.ironfit.ironfit.modelo.asistencia;

import com.ironfit.ironfit.modelo.rutina.Ejercicio;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "asistencia_ejercicio")
public class AsistenciaEjercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia_ejercicio")
    private Long idAsistenciaEjercicio;

    @ManyToOne
    @JoinColumn(name = "id_asistencia", nullable = false)
    private Asistencia asistencia;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio", nullable = false)
    private Ejercicio ejercicio;

    @Column(name = "completado")
    private Boolean completado;

    @Column(name = "repeticiones_realizadas")
    private Short repeticionesRealizadas;

    @Column(name = "peso_usado", precision = 6, scale = 2)
    private BigDecimal pesoUsado;

    @Column(name = "notas", length = 200)
    private String notas;

    public AsistenciaEjercicio() {
    }

    public Long getIdAsistenciaEjercicio() {
        return idAsistenciaEjercicio;
    }

    public void setIdAsistenciaEjercicio(Long idAsistenciaEjercicio) {
        this.idAsistenciaEjercicio = idAsistenciaEjercicio;
    }

    public Asistencia getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(Asistencia asistencia) {
        this.asistencia = asistencia;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public Short getRepeticionesRealizadas() {
        return repeticionesRealizadas;
    }

    public void setRepeticionesRealizadas(Short repeticionesRealizadas) {
        this.repeticionesRealizadas = repeticionesRealizadas;
    }

    public BigDecimal getPesoUsado() {
        return pesoUsado;
    }

    public void setPesoUsado(BigDecimal pesoUsado) {
        this.pesoUsado = pesoUsado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}

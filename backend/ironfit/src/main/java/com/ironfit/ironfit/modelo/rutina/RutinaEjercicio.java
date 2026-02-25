package com.ironfit.ironfit.modelo.rutina;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "rutina_ejercicio")
public class RutinaEjercicio {

    @EmbeddedId
    private RutinaEjercicioId id;

    @ManyToOne
    @MapsId("idRutina")
    @JoinColumn(name = "id_rutina")
    private Rutina rutina;

    @ManyToOne
    @MapsId("idEjercicio")
    @JoinColumn(name = "id_ejercicio")
    private Ejercicio ejercicio;

    @Column(name = "series")
    private Short series;

    @Column(name = "repeticiones", length = 20)
    private String repeticiones;

    @Column(name = "tiempo_seg")
    private Short tiempoSeg;

    @Column(name = "peso_objetivo", precision = 6, scale = 2)
    private BigDecimal pesoObjetivo;

    @Column(name = "descanso_seg")
    private Short descansoSeg;

    public RutinaEjercicio() {
    }

    public RutinaEjercicioId getId() {
        return id;
    }

    public void setId(RutinaEjercicioId id) {
        this.id = id;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Short getSeries() {
        return series;
    }

    public void setSeries(Short series) {
        this.series = series;
    }

    public String getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(String repeticiones) {
        this.repeticiones = repeticiones;
    }

    public Short getTiempoSeg() {
        return tiempoSeg;
    }

    public void setTiempoSeg(Short tiempoSeg) {
        this.tiempoSeg = tiempoSeg;
    }

    public BigDecimal getPesoObjetivo() {
        return pesoObjetivo;
    }

    public void setPesoObjetivo(BigDecimal pesoObjetivo) {
        this.pesoObjetivo = pesoObjetivo;
    }

    public Short getDescansoSeg() {
        return descansoSeg;
    }

    public void setDescansoSeg(Short descansoSeg) {
        this.descansoSeg = descansoSeg;
    }
}


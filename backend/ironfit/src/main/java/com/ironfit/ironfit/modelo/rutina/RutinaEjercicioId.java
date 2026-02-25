package com.ironfit.ironfit.modelo.rutina;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RutinaEjercicioId implements Serializable {

    private Long idRutina;
    private Long idEjercicio;
    private Short orden;

    public RutinaEjercicioId() {
    }

    public Long getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(Long idRutina) {
        this.idRutina = idRutina;
    }

    public Long getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Long idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }
}


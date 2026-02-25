package com.ironfit.ironfit.modelo.seguridad;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PermisoRolId implements Serializable {

    private Long idRol;
    private Long idPermiso;

    public PermisoRolId() {}

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }
}

package com.ironfit.ironfit.modelo.seguridad;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UsuarioRolId implements Serializable {

    private Long idUsuario;
    private Long idRol;

    public UsuarioRolId() {}

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }
}


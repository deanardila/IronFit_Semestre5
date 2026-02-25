package com.ironfit.ironfit.modelo.seguridad;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UsuarioEspecialidadId implements Serializable {

    private Long idUsuario;
    private Long idEspecialidad;

    public UsuarioEspecialidadId() {}

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
}

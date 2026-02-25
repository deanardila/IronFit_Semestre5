package com.ironfit.ironfit.modelo.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario_especialidad")
public class UsuarioEspecialidad {

    @EmbeddedId
    private UsuarioEspecialidadId id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idEspecialidad")
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;

    public UsuarioEspecialidad() {}

    public UsuarioEspecialidadId getId() {
        return id;
    }

    public void setId(UsuarioEspecialidadId id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }
}

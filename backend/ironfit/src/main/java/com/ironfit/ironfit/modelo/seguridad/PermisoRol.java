package com.ironfit.ironfit.modelo.seguridad;

import jakarta.persistence.*;

@Entity
@Table(name = "permiso_rol")
public class PermisoRol {

    @EmbeddedId
    private PermisoRolId id;

    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "id_rol")
    private Rol rol;

    @ManyToOne
    @MapsId("idPermiso")
    @JoinColumn(name = "id_permiso")
    private Permiso permiso;

    public PermisoRol() {}

    public PermisoRolId getId() {
        return id;
    }

    public void setId(PermisoRolId id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }
}

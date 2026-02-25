package com.ironfit.ironfit.modelo.seguridad;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "permiso")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_permiso")
    private Long idPermiso;

    @Column(name = "nombre", length = 120, nullable = false, unique = true)
    private String nombre;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "permiso")
    private List<PermisoRol> roles;

    public Permiso() {}

    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<PermisoRol> getRoles() {
        return roles;
    }

    public void setRoles(List<PermisoRol> roles) {
        this.roles = roles;
    }
}

package com.ironfit.ironfit.modelo.seguridad;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "nombre_rol", length = 40, nullable = false, unique = true)
    private String nombreRol;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "rol")
    private List<UsuarioRol> usuarios;

    @OneToMany(mappedBy = "rol")
    private List<PermisoRol> permisos;

    public Rol() {}

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public List<UsuarioRol> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioRol> usuarios) {
        this.usuarios = usuarios;
    }

    public List<PermisoRol> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisoRol> permisos) {
        this.permisos = permisos;
    }
}


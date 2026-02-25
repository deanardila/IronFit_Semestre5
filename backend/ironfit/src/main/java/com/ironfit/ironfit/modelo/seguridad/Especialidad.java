package com.ironfit.ironfit.modelo.seguridad;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "especialidad")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidad")
    private Long idEspecialidad;

    @Column(name = "nombre", length = 80, nullable = false, unique = true)
    private String nombre;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "especialidad")
    private List<UsuarioEspecialidad> usuarios;

    public Especialidad() {}

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
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

    public List<UsuarioEspecialidad> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioEspecialidad> usuarios) {
        this.usuarios = usuarios;
    }
}

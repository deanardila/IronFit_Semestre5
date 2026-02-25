package com.ironfit.ironfit.modelo.rutina;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "grupo_muscular")
public class GrupoMuscular {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_grupo_muscular")
    private Long idGrupoMuscular;

    @Column(name = "nombre", length = 80, nullable = false)
    private String nombre;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "grupoMuscular")
    private List<Ejercicio> ejercicios;

    public GrupoMuscular() {
    }

    public Long getIdGrupoMuscular() {
        return idGrupoMuscular;
    }

    public void setIdGrupoMuscular(Long idGrupoMuscular) {
        this.idGrupoMuscular = idGrupoMuscular;
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

    public List<Ejercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
}

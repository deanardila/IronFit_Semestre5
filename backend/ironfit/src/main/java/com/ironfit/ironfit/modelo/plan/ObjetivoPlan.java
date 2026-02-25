package com.ironfit.ironfit.modelo.plan;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "objetivo_plan")
public class ObjetivoPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_objetivo")
    private Long idObjetivo;

    @Column(name = "nombre", length = 60, nullable = false, unique = true)
    private String nombre; // "Bajar de peso", "Ganar masa", etc.

    @OneToMany(mappedBy = "objetivo")
    private List<PlanEntrenamiento> planesEntrenamiento;

    public ObjetivoPlan() {
    }

    public Long getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(Long idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<PlanEntrenamiento> getPlanesEntrenamiento() {
        return planesEntrenamiento;
    }

    public void setPlanesEntrenamiento(List<PlanEntrenamiento> planesEntrenamiento) {
        this.planesEntrenamiento = planesEntrenamiento;
    }

}

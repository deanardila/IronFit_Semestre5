package com.ironfit.ironfit.modelo.plan;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "estado_plan")
public class EstadoPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado_plan")
    private Long idEstadoPlan;

    @Column(name = "nombre", length = 30, nullable = false, unique = true)
    private String nombre; // "ACTIVO", "FINALIZADO", "PAUSADO", etc.

    @OneToMany(mappedBy = "estadoPlan")
    private List<PlanEntrenamiento> planesEntrenamiento;

    public EstadoPlan() {
    }

    public Long getIdEstadoPlan() {
        return idEstadoPlan;
    }

    public void setIdEstadoPlan(Long idEstadoPlan) {
        this.idEstadoPlan = idEstadoPlan;
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

    @Override
    public String toString() {
        return nombre;
    }

}

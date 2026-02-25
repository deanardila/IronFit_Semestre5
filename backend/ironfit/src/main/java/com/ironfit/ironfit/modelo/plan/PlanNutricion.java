package com.ironfit.ironfit.modelo.plan;

import jakarta.persistence.*;

@Entity
@Table(name = "plan_nutricion")
public class PlanNutricion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nutricion")
    private Long idNutricion;

    @ManyToOne
    @JoinColumn(name = "id_plan", nullable = false)
    private PlanEntrenamiento planEntrenamiento;

    @Column(name = "kcal_objetivo")
    private Short kcalObjetivo;

    @Column(name = "proteina_g")
    private Short proteinaG;

    @Column(name = "carbo_g")
    private Short carboG;

    @Column(name = "grasa_g")
    private Short grasaG;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    public PlanNutricion() {
    }

    public Long getIdNutricion() {
        return idNutricion;
    }

    public void setIdNutricion(Long idNutricion) {
        this.idNutricion = idNutricion;
    }

    public PlanEntrenamiento getPlanEntrenamiento() {
        return planEntrenamiento;
    }

    public void setPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        this.planEntrenamiento = planEntrenamiento;
    }

    public Short getKcalObjetivo() {
        return kcalObjetivo;
    }

    public void setKcalObjetivo(Short kcalObjetivo) {
        this.kcalObjetivo = kcalObjetivo;
    }

    public Short getProteinaG() {
        return proteinaG;
    }

    public void setProteinaG(Short proteinaG) {
        this.proteinaG = proteinaG;
    }

    public Short getCarboG() {
        return carboG;
    }

    public void setCarboG(Short carboG) {
        this.carboG = carboG;
    }

    public Short getGrasaG() {
        return grasaG;
    }

    public void setGrasaG(Short grasaG) {
        this.grasaG = grasaG;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}


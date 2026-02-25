package com.ironfit.ironfit.modelo.rutina;

import jakarta.persistence.*;
import java.util.List;

import com.ironfit.ironfit.modelo.plan.PlanEntrenamiento;

@Entity
@Table(name = "rutina")
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rutina")
    private Long idRutina;

    @ManyToOne
    @JoinColumn(name = "id_plan", nullable = false)
    private PlanEntrenamiento planEntrenamiento;

    @Column(name = "nombre", length = 120, nullable = false)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "orden")
    private Short orden;

    @OneToMany(mappedBy = "rutina")
    private List<RutinaEjercicio> rutinaEjercicios;

    @OneToMany(mappedBy = "rutina")
    private List<Sesion> sesiones;

    public Rutina() {
    }

    public Long getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(Long idRutina) {
        this.idRutina = idRutina;
    }

    public PlanEntrenamiento getPlanEntrenamiento() {
        return planEntrenamiento;
    }

    public void setPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        this.planEntrenamiento = planEntrenamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }

    public List<RutinaEjercicio> getRutinaEjercicios() {
        return rutinaEjercicios;
    }

    public void setRutinaEjercicios(List<RutinaEjercicio> rutinaEjercicios) {
        this.rutinaEjercicios = rutinaEjercicios;
    }

    public List<Sesion> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<Sesion> sesiones) {
        this.sesiones = sesiones;
    }
}

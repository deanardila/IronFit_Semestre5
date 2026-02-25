package com.ironfit.ironfit.modelo.plan;

import com.ironfit.ironfit.modelo.seguridad.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "plan_entrenamiento")
public class PlanEntrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Long idPlan;

    // Usuario con rol CLIENTE
    @ManyToOne
    @JoinColumn(name = "id_usuario_cliente", nullable = false)
    private Usuario usuarioCliente;

    // Usuario con rol ENTRENADOR
    @ManyToOne
    @JoinColumn(name = "id_usuario_entrenador", nullable = false)
    private Usuario usuarioEntrenador;

    @ManyToOne
    @JoinColumn(name = "id_objetivo", nullable = false)
    private ObjetivoPlan objetivo;

    @ManyToOne
    @JoinColumn(name = "id_estado_plan", nullable = false)
    private EstadoPlan estadoPlan;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "nombre", length = 120, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    // Relación con PlanNutricion (un plan puede tener 1 a n registros de nutrición)
    @OneToMany(mappedBy = "planEntrenamiento")
    private List<PlanNutricion> planesNutricion;

    public PlanEntrenamiento() {
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public Usuario getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setUsuarioCliente(Usuario usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }

    public Usuario getUsuarioEntrenador() {
        return usuarioEntrenador;
    }

    public void setUsuarioEntrenador(Usuario usuarioEntrenador) {
        this.usuarioEntrenador = usuarioEntrenador;
    }

    public ObjetivoPlan getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(ObjetivoPlan objetivo) {
        this.objetivo = objetivo;
    }

    public EstadoPlan getEstadoPlan() {
        return estadoPlan;
    }

    public void setEstadoPlan(EstadoPlan estadoPlan) {
        this.estadoPlan = estadoPlan;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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

    public List<PlanNutricion> getPlanesNutricion() {
        return planesNutricion;
    }

    public void setPlanesNutricion(List<PlanNutricion> planesNutricion) {
        this.planesNutricion = planesNutricion;
    }
}

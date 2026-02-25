package com.ironfit.ironfit.modelo.asistencia;

import com.ironfit.ironfit.modelo.rutina.Sesion;
import com.ironfit.ironfit.modelo.seguridad.Usuario;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asistencia")
    private Long idAsistencia;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_sesion", nullable = false)
    private Sesion sesion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "observacion", length = 200)
    private String observacion;

    // NUEVO: si cumplió o no la rutina
    @Column(name = "cumplio_rutina")
    private Boolean cumplioRutina;

    @OneToMany(mappedBy = "asistencia")
    private List<AsistenciaEjercicio> ejercicios;

    public Asistencia() {
    }

    public Long getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(Long idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Sesion getSesion() {
        return sesion;
    }

    public void setSesion(Sesion sesion) {
        this.sesion = sesion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Boolean getCumplioRutina() {
        return cumplioRutina;
    }

    public void setCumplioRutina(Boolean cumplioRutina) {
        this.cumplioRutina = cumplioRutina;
    }

    public List<AsistenciaEjercicio> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<AsistenciaEjercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }
}

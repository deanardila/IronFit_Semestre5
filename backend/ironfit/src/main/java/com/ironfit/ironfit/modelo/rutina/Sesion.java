package com.ironfit.ironfit.modelo.rutina;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

import com.ironfit.ironfit.modelo.asistencia.Asistencia;

@Entity
@Table(name = "sesion")
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sesion")
    private Long idSesion;

    @ManyToOne
    @JoinColumn(name = "id_rutina", nullable = false)
    private Rutina rutina;

    @ManyToOne
    @JoinColumn(name = "id_dia", nullable = false)
    private DiaSemana diaSemana;

    @Column(name = "hora")
    private LocalTime hora;

    @Column(name = "notas", length = 200)
    private String notas;

    @OneToMany(mappedBy = "sesion")
    private List<Asistencia> asistencias;

    public Sesion() {
    }

    public Long getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    public Rutina getRutina() {
        return rutina;
    }

    public void setRutina(Rutina rutina) {
        this.rutina = rutina;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public List<Asistencia> getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(List<Asistencia> asistencias) {
        this.asistencias = asistencias;
    }
}
